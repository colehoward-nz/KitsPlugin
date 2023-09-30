package me.cole.kitsplugin.titles;

import me.cole.kitsplugin.database.Database;
import me.cole.kitsplugin.database.DatabaseStructure;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TitleManager {
    private Database database;

    public TitleManager(Database database){
        this.database = database;
    }

    public int getKills(String title){
        int kills = 0;

        if (title.equalsIgnoreCase("peasant")) {
            kills = 50;
        } else if (title.equalsIgnoreCase("amateur")) {
            kills = 100;
        } else if (title.equalsIgnoreCase("recruit")) {
            kills = 200;
        } else if (title.equalsIgnoreCase("elite")) {
            kills = 500;
        } else if (title.equalsIgnoreCase("decimator")) {
            kills = 1000;
        } else if (title.equalsIgnoreCase("god")) {
            kills = 9999;
        }

        return kills;
    }

    public String getTitle(Player player) {
        String title = "Peasant";
        int kills;
        try {
            DatabaseStructure stats = database.getUserStatistics(player);
            kills = stats.getKills();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if ((0 <= kills && kills <= 49)) {
            title = "Peasant";
        } else if ((50 <= kills && kills <= 99)) {
            title = "Amateur";
        } else if ((100 <= kills && kills <= 199)) {
            title = "Recruit";
        } else if ((200 <= kills && kills <= 499)) {
            title = "Elite";
        } else if ((500 <= kills && kills <= 999)) {
            title = "Decimator";
        } else if ((999 <= kills && kills <= 9999)) {
            title = "God";
        }

        return title;
    }

    public String getColouredTitle(Player player) {
        String getTitle = getTitle(player);
        String colouredTitle = "";
        switch (getTitle) {
            case "Peasant" ->
                    colouredTitle = ChatColor.GRAY +"Peasant";
            case "Amateur" ->
                    colouredTitle = ChatColor.GREEN +"Amateur";
            case "Recruit" ->
                    colouredTitle = ChatColor.GOLD +"Recruit";
            case "Elite" ->
                    colouredTitle = ChatColor.BLUE +"Elite";
            case "Decimator" ->
                    colouredTitle = ChatColor.DARK_PURPLE +"Decimator";
            case "God" ->
                    colouredTitle = ChatColor.RED +"God";

        }
        return colouredTitle;
    }
}
