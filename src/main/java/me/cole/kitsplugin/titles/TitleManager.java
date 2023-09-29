package me.cole.kitsplugin.titles;

import me.cole.kitsplugin.database.Database;
import org.bukkit.entity.Player;

public class TitleManager {
    private Database database;

    public TitleManager(Database database){
        this.database = database;
    }

    public void checkTitle(Player player) {

    }

    public String getTitle(Player player) {
        return "";
    }

    public String getColouredTitle(Player player) {
        return "";
    }
}
