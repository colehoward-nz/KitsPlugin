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

    public enum TitleTypes {
        PEASANT("Peasant"), AMATEUR("Amateur"), RECRUIT("Recruit"), ELITE("Elite"), DECIMATOR("Decimator"), GOD("God");

        private String title;
        TitleTypes(String title) {
        }
    }

    public int getKills(String title){
        int kills = 0;
        if (title.equalsIgnoreCase("peasant")) {
            return kills = 50;
        } else if (title.equalsIgnoreCase("amateur")) {
            return kills = 100;
        } else if (title.equalsIgnoreCase("recruit")) {
            return kills = 200;
        } else if (title.equalsIgnoreCase("elite")) {
            return kills = 500;
        } else if (title.equalsIgnoreCase("decimator")) {
            return kills = 1000;
        } else if (title.equalsIgnoreCase("god")) {
            return kills = 9999;
        }

        return kills;
    }

    public String getTitle(Player player) {
        String title = null;
        try {
            DatabaseStructure stats = database.getUserStatistics(player);
            int kills = stats.getKills();

            if ((0 <= kills && kills <= 49)) {
                title = TitleTypes.PEASANT.title;
            } else if ((50 <= kills && kills <= 99)) {
                title = TitleTypes.AMATEUR.title;
            } else if ((100 <= kills && kills <= 199)) {
                title = TitleTypes.RECRUIT.title;
            } else if ((200 <= kills && kills <= 499)) {
                title = TitleTypes.ELITE.title;
            } else if ((500 <= kills && kills <= 999)) {
                title = TitleTypes.DECIMATOR.title;
            } else if ((999 <= kills && kills <= 9999)) {
                title = TitleTypes.GOD.title;
            } else {
                title = TitleTypes.PEASANT.title;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
