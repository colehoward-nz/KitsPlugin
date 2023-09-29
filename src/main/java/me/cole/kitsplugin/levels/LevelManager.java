package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.Kits;
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
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
            return (int)Math.ceil(Math.pow((level/0.7), 2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int setExp(Player player, int exp) {
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
            stats.setExp(exp);
            stats.setLevel((int)Math.ceil(0.03 * Math.sqrt(exp)));
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


}
