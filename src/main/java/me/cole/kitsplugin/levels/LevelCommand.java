package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.Kits;
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
        if (sender instanceof Player player) {
            if (args.length == 0 || args[0].equalsIgnoreCase(player.getDisplayName())) {
                player.sendMessage(ChatColor.YELLOW + "You are currently LEVEL " + plugin.lm.checkLevel(player) + " (" + plugin.lm.getExp(player) + "/" +
                        plugin.lm.getTargetLevelExp(player, plugin.lm.getLevel(player)) + ")");
            }
        }

        return true;
    }
}
