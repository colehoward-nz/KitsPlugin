package me.cole.kitsplugin;

import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.levels.commands.LevelCommand;
import me.cole.kitsplugin.levels.LevelManager;
import me.cole.kitsplugin.levels.commands.SetExpCommand;
import me.cole.kitsplugin.levels.commands.SetLevelCommand;
import me.cole.kitsplugin.listeners.OnPlayerChat;
import me.cole.kitsplugin.listeners.OnPlayerDeath;
import me.cole.kitsplugin.titles.TitleManager;
import me.cole.kitsplugin.titles.commands.TitleCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class Kits extends JavaPlugin {
    private Database database;
    private Kits plugin;
    public LevelManager lm;
    public TitleManager tm;



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
        tm = new TitleManager(database);

        // Register commands
        Objects.requireNonNull(getCommand("title")).setExecutor(new TitleCommand(this, database));
        Objects.requireNonNull(getCommand("level")).setExecutor(new LevelCommand(this));
        Objects.requireNonNull(getCommand("setlevel")).setExecutor(new SetLevelCommand(this));
        Objects.requireNonNull(getCommand("setxp")).setExecutor(new SetExpCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new OnPlayerChat(plugin), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDeath(database), this);

        // Setup config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    public Database getDatabase() {
        return this.database;
    }
}
