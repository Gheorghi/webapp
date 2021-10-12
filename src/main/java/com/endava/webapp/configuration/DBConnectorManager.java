package com.endava.webapp.configuration;

import com.endava.webapp.repository.exception.DBConnectionFailedException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectorManager {

    private Configuration config = new Configuration();

    private DBConnectorManager() {
    }

    private static final class SingletonHolder {
        private static final DBConnectorManager SINGLETON = new DBConnectorManager();
    }

    public static DBConnectorManager getInstance() {
        return SingletonHolder.SINGLETON;
    }

    public Connection getConnection() throws SQLException {
        String url = null;
        String username = null;
        String password = null;
        try {
            url = config.getConfigProperties("url");
            username = config.getConfigProperties("username");
            password = config.getConfigProperties("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            var con = DriverManager.getConnection(url, username, password);
            con.setAutoCommit(false);
            return con;
        } catch (final SQLException e) {
            throw e;
        }
    }
}
