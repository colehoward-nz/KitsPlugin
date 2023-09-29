package me.cole.kitsplugin.levels;

import me.cole.kitsplugin.Kits;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class SetExpCommand implements CommandExecutor {
    private Kits plugin;

    public SetExpCommand(Kits plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player player) {

        }
        return true;
    }
}
