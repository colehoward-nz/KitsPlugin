package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.Kits;
import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.database.DatabaseStructure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Set;

public class SetExpCommand implements CommandExecutor {
    private final Kits plugin;
    private final Database database;
    private final String playerNotFound;

    public SetExpCommand(Kits plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
        this.playerNotFound = plugin.getConfig().getString("player-not-found");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0 || args.length == 1) {
                player.sendMessage(ChatColor.RED + "Incorrect usage: /setexp <player> <number>");
            }
            else {
                if (Bukkit.getServer().getPlayerExact(args[0]) != null) {
                    int exp = Integer.parseInt(args[1]);
                    plugin.lm.setExp(player, exp);
                }
                else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', playerNotFound + args[0]));
                }
            }
        }
        return true;
    }
}
