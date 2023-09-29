package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.Kits;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand implements CommandExecutor {
    private Kits plugin;

    public LevelCommand(Kits plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player player) {
            plugin.lm.checkLevel(player);
        }
        return false;
    }
}
