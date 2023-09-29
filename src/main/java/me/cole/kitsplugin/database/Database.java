package me.cole.kitsplugin.database;

import org.bukkit.entity.Player;

import java.sql.*;

public class Database {
    private Connection connection;

    public Connection getConnection() throws SQLException
    {
        if (connection != null)
        {
            return connection;
        }

        String url = "jdbc:mysql://localhost/kitpvp";
        String username = "root";
        String password = "";

        this.connection = DriverManager.getConnection(url, username, password);
        return this.connection;
    }

    public void initialiseDatabase() throws SQLException
    {
        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS player_statistics(userUUID varchar(200) primary key, userGroup varchar(200), kills int, deaths int, level int, exp int, bal int)";
        statement.execute(sql);

        System.out.println("[KitPvP] MySQL server connection and initialisation completed successfully.");
        statement.close();
    }

    public DatabaseStructure getUserStatistics(Player player) throws SQLException {
        return searchUUID(player.getUniqueId().toString());
    }

    public DatabaseStructure searchUUID(String userUUID) throws SQLException
    {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM player_statistics WHERE userUUID = ?");
        preparedStatement.setString(1, userUUID);
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseStructure databaseStructure;

        if (resultSet.next())
        {
            databaseStructure = new DatabaseStructure(resultSet.getString("userUUID"),
                    resultSet.getString("userGroup"), resultSet.getInt("kills"), resultSet.getInt("deaths"),
                    resultSet.getInt("level"), resultSet.getInt("exp"), resultSet.getInt("bal"));
            preparedStatement.close();

            return databaseStructure;
        }

        preparedStatement.close();
        return null;
    }

    public void updateUserStatistics(DatabaseStructure databaseStructure) throws SQLException
    {

        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE player_statistics SET userUUID = ?, userGroup = ?, kills = ?, deaths = ?, " +
                "level = ?, exp = ?, bal = ?");
        preparedStatement.setString(1, databaseStructure.getUserUUID());
        preparedStatement.setString(2, databaseStructure.getUserGroup());
        preparedStatement.setInt(3, databaseStructure.getKills());
        preparedStatement.setInt(4, databaseStructure.getDeaths());
        preparedStatement.setInt(5, databaseStructure.getLevel());
        preparedStatement.setInt(6, databaseStructure.getExp());
        preparedStatement.setInt(7, databaseStructure.getBal());

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }
}
