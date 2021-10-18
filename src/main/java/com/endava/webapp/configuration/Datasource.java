package com.endava.webapp.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Getter
@Component
public class Datasource {
    private Connection connection;

    public Datasource(@Value("${oracle.url}") final String url,
                      @Value("${oracle.username}") final String username,
                      @Value("${oracle.password}") final String password) {
        try {
            val con = DriverManager.getConnection(url, username, password);
            con.setAutoCommit(false);
            log.info("Connection to DB established successfully");
            connection = con;
        } catch (final SQLException e) {
            log.error("Database connection failure, caused by: {}", e.getMessage());
            System.exit(-1);
        }
    }
}
