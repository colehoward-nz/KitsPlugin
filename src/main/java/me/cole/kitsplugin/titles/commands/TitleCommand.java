package me.cole.kitsplugin.titles.commands;

import me.cole.kitsplugin.Kits;
import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.database.DatabaseStructure;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TitleCommand implements CommandExecutor {
    private final Kits plugin;
    private final Database database;


    public TitleCommand(Kits plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            String getTitle = plugin.tm.getTitle(player);
            String getColouredTitle = plugin.tm.getColouredTitle(player);
            int getKills = plugin.tm.getKills(getTitle);
            int differKills = 0;

            DatabaseStructure stats = null;
            int kills;
            try {
                stats = database.getUserStatistics(player);
                differKills = getKills-stats.getKills();
                kills = stats.getKills();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            player.sendMessage(ChatColor.YELLOW + "You are titled " + getColouredTitle.toUpperCase() + ChatColor.YELLOW +
                    ".\n\nTo improve your title you must reach " + ChatColor.GREEN + getKills + ChatColor.YELLOW +
                    " kills, \nyou currently have " + ChatColor.GREEN + kills + "/" + getKills + ChatColor.YELLOW + " kills you need " +
                    ChatColor.GREEN + differKills + ChatColor.YELLOW + " \nmore kills to proceed");

}
        return true;
    }
}
