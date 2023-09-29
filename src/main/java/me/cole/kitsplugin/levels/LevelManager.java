package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.Kits;
import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.database.DatabaseStructure;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class LevelManager {
    private final Kits plugin;
    private Database database;

    public LevelManager(Kits plugin, Database database){
        this.plugin = plugin;
        this.database = database;
    }

    public int getTargetLevelExp(DatabaseStructure stats) {
        int targetLevel = stats.getLevel()+1;
        return (int)Math.ceil(Math.pow((targetLevel/0.07), 2));
    }

    public int setExp(Player player, int exp) {
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
            stats.setExp(exp);
            stats.setLevel((int)Math.ceil(0.07 * Math.sqrt(exp)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stats.getExp();
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

    public int checkLevel(Player player) {
        DatabaseStructure stats;
        try {
            stats = database.getUserStatistics(player);
            double exp = stats.getExp();
            if (exp >= getTargetLevelExp(stats)) {
                int remainder = (int) Math.ceil(exp % getTargetLevelExp(stats));
                stats.setLevel(stats.getLevel() + 1);
                stats.setExp(remainder);
                player.sendMessage(ChatColor.AQUA + "You have leveled up!\n" + ChatColor.GREEN + "You are now Level " + stats.getLevel() + "!\n" +
                        ChatColor.GREEN + "(" + stats.getExp() + "/" + getTargetLevelExp(stats) + ")");
                database.updateUserStatistics(stats);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stats.getLevel();
    }


}
