package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.database.DatabaseStructure;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

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
            player.sendMessage("poop");
            stats = database.getUserStatistics(player);
            stats.setExp(exp);
            database.updateUserStatistics(stats);
            //stats.setLevel((int)Math.ceil(0.03 * Math.sqrt(exp)));
            //database.updateUserStatistics(stats);
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
            double exp = stats.getExp();
            if (exp >= getTargetLevelExp(player, getLevel(player))) {
                int remainder = (int) Math.ceil(exp % getTargetLevelExp(player, getLevel(player)));
                stats.setLevel(stats.getLevel() + 1);
                stats.setExp(remainder);
                player.sendMessage(ChatColor.AQUA + "You have leveled up!\n" + ChatColor.GREEN + "You are now Level " + stats.getLevel() + "!\n" +
                        ChatColor.GREEN + "(" + stats.getExp() + "/" + getTargetLevelExp(player, getLevel(player)+1) + ")");
                database.updateUserStatistics(stats);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stats.getLevel();
    }

    public String getColouredLevel(Player player) {
        String colouredLevel = "";
        int level = checkLevel(player);
        if (0 <= level && level < 4) {
            colouredLevel = "" + ChatColor.DARK_GRAY + checkLevel(player);
        } else if (5 <= level && level < 9) {
            colouredLevel = "" + ChatColor.GRAY + checkLevel(player);
        } else if (10 <= level && level < 14) {
            colouredLevel = "" + ChatColor.DARK_GREEN + checkLevel(player);
        } else if (15 <= level && level < 19) {
            colouredLevel = "" + ChatColor.GREEN + checkLevel(player);
        } else if (20 <= level && level < 24) {
            colouredLevel = "" + ChatColor.YELLOW + checkLevel(player);
        } else if (25 <= level && level < 29) {
            colouredLevel = "" + ChatColor.GOLD + checkLevel(player);
        } else if (30 <= level && level < 34) {
            colouredLevel = "" + ChatColor.RED + checkLevel(player);
        } else if (35 <= level && level < 39) {
            colouredLevel = "" + ChatColor.RED + ChatColor.BOLD + checkLevel(player);
        } else {
            colouredLevel = "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + checkLevel(player);
        }
        return colouredLevel;
    }
}
