package ru.clevertec.servlet.connection;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public enum SingletonConnection {
    INSTANCE;

    /**
     * Поле для пула соединений
     */
    private final PGSimpleDataSource dataSource = new PGSimpleDataSource();


    SingletonConnection() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String serverName = resourceBundle.getString("db.serverName");
        int port = Integer.parseInt(resourceBundle.getString("db.port"));
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        String databaseName = resourceBundle.getString("db.databaseName");
        dataSource.setServerNames(new String[]{serverName});
        dataSource.setPortNumbers(new int[]{port});
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(user);
        dataSource.setPassword(password);
    }


    public Connection getConnectionDB() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
