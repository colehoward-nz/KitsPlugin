package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.Kits;
import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.database.DatabaseStructure;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class AddExpCommand implements CommandExecutor {
    private Database database;
    private Kits plugin;

    public AddExpCommand(Kits plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player player) {
            int xp = 0;
            try {
                DatabaseStructure stats = database.getUserStatistics(player);
                xp = Integer.parseInt(strings[0]);
                stats.setExp(xp);
                database.updateUserStatistics(stats);

                player.sendMessage("set xp to " + xp);
                player.sendMessage("xp: " + stats.getExp());
                player.sendMessage("level: " + stats.getLevel());
                player.sendMessage("targetxp: " + plugin.lm.getTargetLevelExp(stats));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
