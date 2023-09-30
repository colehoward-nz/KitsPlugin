package me.cole.kitsplugin.listeners;

import me.cole.kitsplugin.Kits;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnPlayerChat implements Listener {
    private final Kits plugin;

    public OnPlayerChat(Kits plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerChat(AsyncPlayerChatEvent e) {
        String playerLevel = ChatColor.GRAY + "[" + plugin.lm.getColouredLevel(e.getPlayer()) + ChatColor.BOLD + "✩" + ChatColor.GRAY + "] "
                           + ChatColor.GRAY + "[" + plugin.tm.getColouredTitle(e.getPlayer()) + ChatColor.GRAY + "]";
        e.setFormat(playerLevel + e.getFormat());
    }
}
