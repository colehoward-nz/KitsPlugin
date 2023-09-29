package me.cole.kitsplugin.kd.listeners;

import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.database.DatabaseStructure;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;
import java.util.Objects;

public class OnPlayerDeath implements Listener {
    private final Database database;

    public OnPlayerDeath(Database database) {
        this.database = database;
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent e) {
        try {
            DatabaseStructure victimStats = database.getUserStatistics(Objects.requireNonNull(e.getEntity().getPlayer()));
            DatabaseStructure attackerStats = database.getUserStatistics(Objects.requireNonNull(e.getEntity().getKiller()));

            int expLoss = (int)Math.floor(Math.random() *(3 - 1 + 1) + 3);
            victimStats.setExp(victimStats.getExp()-expLoss);
            attackerStats.setExp(attackerStats.getExp()+(expLoss*2));
            attackerStats.setBal((int)(Math.ceil(attackerStats.getBal()+(expLoss*3.5))));

            e.getEntity().getPlayer().sendMessage(ChatColor.RED + "You lost " + expLoss + " exp from dying!");
            e.getEntity().getKiller().sendMessage(ChatColor.RED + "You gained " + (expLoss*2) + " exp from dying!");
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
}
