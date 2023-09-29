package me.cole.kitsplugin;

import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.levels.LevelCommand;
import me.cole.kitsplugin.levels.LevelManager;
import me.cole.kitsplugin.levels.SetExpCommand;
import me.cole.kitsplugin.levels.SetLevelCommand;
import me.cole.kitsplugin.listeners.OnPlayerChat;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class Kits extends JavaPlugin {
    private Database database;
    private Kits plugin;
    public LevelManager lm;


    @Override
    public void onEnable() {
        try {
            this.database = new Database();
            database.initialiseDatabase();
        }
        catch (SQLException exception) {
            System.out.println("[KitPvP] Unable to connect to MySQL server.");
            exception.printStackTrace();
        }

        plugin = this;
        lm = new LevelManager(database);

        // Register commands
        Objects.requireNonNull(getCommand("level")).setExecutor(new LevelCommand(this));
        Objects.requireNonNull(getCommand("setlevel")).setExecutor(new SetLevelCommand(this));
        Objects.requireNonNull(getCommand("setxp")).setExecutor(new SetExpCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new OnPlayerChat(plugin), this);

        // Setup config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    public Database getDatabase() {
        return this.database;
    }
}
