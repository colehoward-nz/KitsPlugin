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

    private enum TitleTypes {
        PEASANT, AMATEUR, RECRUIT, ELITE, DECIMATOR, GOD
    }

    public TitleTypes getTitle(Player player) {
        TitleTypes title = null;
        try {
            DatabaseStructure stats = database.getUserStatistics(player);
            int kills = stats.getKills();

            if ((0 <= kills && kills <= 49)) {
                title = TitleTypes.PEASANT;
            } else if ((50 <= kills && kills <= 99)) {
                title = TitleTypes.AMATEUR;
            } else if ((100 <= kills && kills <= 199)) {
                title = TitleTypes.RECRUIT;
            } else if ((200 <= kills && kills <= 499)) {
                title = TitleTypes.ELITE;
            } else if ((500 <= kills && kills <= 999)) {
                title = TitleTypes.DECIMATOR;
            } else if ((999 <= kills && kills <= 9999)) {
                title = TitleTypes.GOD;
            } else {
                title = TitleTypes.PEASANT;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return title;
    }

    public String getColouredTitle(Player player) {
        TitleTypes getTitle = getTitle(player);
        String colouredTitle = "";
        switch (getTitle) {
            case PEASANT ->
                    colouredTitle = ChatColor.GRAY +"Peasant";
            case AMATEUR ->
                    colouredTitle = ChatColor.GREEN +"Amateur";
            case RECRUIT ->
                    colouredTitle = ChatColor.GOLD +"Recruit";
            case ELITE ->
                    colouredTitle = ChatColor.BLUE +"Elite";
            case DECIMATOR ->
                    colouredTitle = ChatColor.DARK_PURPLE +"Decimator";
            case GOD ->
                    colouredTitle = ChatColor.RED +"God";

        }
        return colouredTitle;
    }
}
