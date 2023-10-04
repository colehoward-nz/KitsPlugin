package me.cole.kitsplugin.levels.commands;

import me.cole.kitsplugin.Kits;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand implements CommandExecutor {
    private final Kits plugin;
    private final String playerNotFound;

    public LevelCommand(Kits plugin) {
        this.plugin = plugin;
        this.playerNotFound = plugin.getConfig().getString("player-not-found");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0 || args[0].equalsIgnoreCase(player.getDisplayName())) {
                player.sendMessage(ChatColor.YELLOW + "You are currently LEVEL " + plugin.lm.checkLevel(player) + " (" + plugin.lm.getExp(player) + "/" +
                        plugin.lm.getTargetLevelExp(player, plugin.lm.getLevel(player)) + ")");
            }
            else {
                Player argumentPlayer = Bukkit.getServer().getPlayerExact(args[0]);
                if (argumentPlayer != null) {
                    player.sendMessage(ChatColor.YELLOW + argumentPlayer.getDisplayName() + " is currently LEVEL " + plugin.lm.checkLevel(argumentPlayer) + " (" + plugin.lm.getExp(argumentPlayer) + "/" +
                            plugin.lm.getTargetLevelExp(argumentPlayer, plugin.lm.getLevel(argumentPlayer)) + ")");
                }
                else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', playerNotFound));
                }
            }
        }

        return true;
    }
}
