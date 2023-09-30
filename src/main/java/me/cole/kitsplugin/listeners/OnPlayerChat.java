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
        String playerLevel = ChatColor.WHITE + "[" + plugin.lm.getColouredLevel(e.getPlayer()) + ChatColor.WHITE + "] "
                           + ChatColor.WHITE + "[" + plugin.tm.getColouredTitle(e.getPlayer()) + ChatColor.WHITE + "] ";
        e.setFormat(playerLevel + e.getFormat());
    }
}
