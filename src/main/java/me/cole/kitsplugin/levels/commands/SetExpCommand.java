package me.cole.kitsplugin.levels.commands;

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
    private final String playerNotFound;

    public SetExpCommand(Kits plugin) {
        this.plugin = plugin;
        this.playerNotFound = plugin.getConfig().getString("player-not-found");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("kits.setexp") || player.isOp()) {
                if (args.length == 0 || args.length == 1) {
                    player.sendMessage(ChatColor.RED + "Incorrect usage: /setexp <player> <number>");
                }
                else {
                    Player argumentPlayer = Bukkit.getServer().getPlayerExact(args[0]);
                    if (argumentPlayer != null) {
                        int exp = Integer.parseInt(args[1]);
                        plugin.lm.setExp(player, exp);
                        player.sendMessage(ChatColor.YELLOW + "Exp of " + argumentPlayer.getDisplayName() + " has been set to " + exp);
                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', playerNotFound + args[0]));
                    }
                }
            }
        }
        return true;
    }
}
