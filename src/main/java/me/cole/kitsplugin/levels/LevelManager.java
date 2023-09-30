package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.database.DatabaseStructure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LevelManager {
    private Database database;

    public LevelManager(Database database){
        this.database = database;
    }

    public int getTargetLevelExp(Player player, int level) {
        return (int)Math.ceil(Math.pow((level/0.7), 2));
    }

    public int setExp(Player player, int exp) {
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
            stats.setExp(exp);
            database.updateUserStatistics(stats);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stats.getExp();
    }

    public int getExp(Player player) {
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
            return stats.getExp();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int setLevel(Player player, int level) {
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
            stats.setLevel(level);
            stats.setExp(0);
            database.updateUserStatistics(stats);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stats.getLevel();
    }

    public int getLevel(Player player) {
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
            return stats.getLevel();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkLevel(Player player) {
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        double exp = stats.getExp();
        if (exp >= getTargetLevelExp(player, getLevel(player))) {
            Inventory inventory = player.getInventory();
            if (inventory.firstEmpty() != -1) {
                int remainder = (int) Math.ceil(exp % getTargetLevelExp(player, getLevel(player)));
                stats.setLevel(stats.getLevel() + 1);
                stats.setExp(remainder);

                if (stats.getLevel() % 5 == 0) {
                    int bonusBal = (int) Math.floor(Math.random() *(300 - 100 + 100) + 300);
                    stats.setBal(stats.getBal()+bonusBal);
                    player.sendMessage(ChatColor.YELLOW + "You received a bonus balance reward \nof " + ChatColor.DARK_GREEN
                            + "$" + ChatColor.GREEN + bonusBal + ChatColor.YELLOW + " for reaching Level " + stats.getLevel());
                }

                inventory = player.getInventory();
                inventory.addItem(getRewardCrate());
                player.sendMessage(ChatColor.GREEN + "You are now Level " + stats.getLevel() + "!\n" +
                        ChatColor.GREEN + "(" + stats.getExp() + "/" + getTargetLevelExp(player, getLevel(player) + 1) + ")");

                try {
                    database.updateUserStatistics(stats);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else {
                player.sendMessage(ChatColor.GREEN + "\nYour level up has been halted as \nyour" +
                        " inventory is full.\n ");
            }
        }
        return stats.getLevel();
    }

    public ItemStack getRewardCrate() {
        ItemStack item = new ItemStack(Material.DRIED_KELP_BLOCK, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "[Reward Crate]");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(""); lore.add(ChatColor.AQUA + "Right-click to unlock!"); lore.add("");
        itemMeta.setLore(lore);
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public String getColouredLevel(Player player) {
        String colouredLevel = "";
        int level = getLevel(player);
        if (0 <= level && level <= 4) {
            colouredLevel = "" + ChatColor.DARK_GRAY + getLevel(player);
        } else if (5 <= level && level <= 9) {
            colouredLevel = "" + ChatColor.GRAY + getLevel(player);
        } else if (10 <= level && level <= 14) {
            colouredLevel = "" + ChatColor.DARK_GREEN + getLevel(player);
        } else if (15 <= level && level <= 19) {
            colouredLevel = "" + ChatColor.GREEN + getLevel(player);
        } else if (20 <= level && level <= 24) {
            colouredLevel = "" + ChatColor.YELLOW + getLevel(player);
        } else if (25 <= level && level <= 29) {
            colouredLevel = "" + ChatColor.GOLD + getLevel(player);
        } else if (30 <= level && level <= 34) {
            colouredLevel = "" + ChatColor.RED + getLevel(player);
        } else if (35 <= level && level <= 39) {
            colouredLevel = "" + ChatColor.RED + ChatColor.BOLD + getLevel(player);
        } else {
            colouredLevel = "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + getLevel(player);
        }
        return colouredLevel;
    }
}
