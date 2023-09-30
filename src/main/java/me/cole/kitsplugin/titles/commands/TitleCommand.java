package me.cole.kitsplugin.titles.commands;

import me.cole.kitsplugin.Kits;
import me.cole.kitsplugin.titles.TitleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleCommand implements CommandExecutor {
    private final Kits plugin;

    public TitleCommand(Kits plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            String getTitle = plugin.tm.getTitle(player);
            int getKills = plugin.tm.getKills(getTitle);
            switch (getTitle){
                case "Peasant":
                    player.sendMessage(ChatColor.YELLOW + "You are titled " + getTitle.toUpperCase() + ".\nTo improve your title you must reach " + getKills + " kills.");
            }

        }
        return true;
    }
}
