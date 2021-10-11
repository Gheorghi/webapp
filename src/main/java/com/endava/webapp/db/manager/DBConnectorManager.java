package com.endava.webapp.db.manager;

import com.endava.webapp.exceptions.DBConnectionFailedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectorManager {
    private DBConnectorManager() {
    }

    private static final class SingletonHolder {
        private static final DBConnectorManager SINGLETON = new DBConnectorManager();
    }

    public static DBConnectorManager getInstance() {
        return SingletonHolder.SINGLETON;
    }

    public Connection getConnection() {
        String url = "jdbc:oracle:thin:@//localhost:1521/PDB"; //default port considered here behind the scenes
        String username = "hr";
        String password = "hr";

        try {
            var con = DriverManager.getConnection(url, username, password);
            con.setAutoCommit(false);
            return con;
        } catch (SQLException e) {
            throw new DBConnectionFailedException();
        }
    }
}
