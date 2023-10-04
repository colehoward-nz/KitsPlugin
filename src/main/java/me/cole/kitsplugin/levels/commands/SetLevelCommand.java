package me.cole.kitsplugin.levels.commands;

import me.cole.kitsplugin.Kits;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLevelCommand implements CommandExecutor {
    private final Kits plugin;
    private final String playerNotFound;

    public SetLevelCommand(Kits plugin) {
        this.plugin = plugin;
        this.playerNotFound = plugin.getConfig().getString("player-not-found");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("kits.setlevel") || player.isOp()) {
                if (args.length == 0 || args.length == 1) {
                    player.sendMessage(ChatColor.RED + "Incorrect usage: /setlevel <player> <number>");
                }
                else {
                    Player argumentPlayer = Bukkit.getServer().getPlayerExact(args[0]);
                    if (argumentPlayer != null) {
                        int level = Integer.parseInt(args[1]);
                        plugin.lm.setLevel(player, level);
                        player.sendMessage(ChatColor.YELLOW + "Level of " + argumentPlayer.getDisplayName() + " has been set to " + level);
                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', playerNotFound));
                    }
                }
            }
        }
        return true;
    }
}
