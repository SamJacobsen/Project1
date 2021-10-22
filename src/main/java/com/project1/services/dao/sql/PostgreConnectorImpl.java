package com.project1.services.dao.sql;

import org.postgresql.Driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreConnectorImpl implements DBConnector{

    private String username;
    private String password;
    private String url;

    private static Properties props = new Properties();

    static {
        try {
            props.load(ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties"));

            DriverManager.registerDriver(new Driver());
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Could not initialize the jdbc driver", throwables);
        }
    }

    public PostgreConnectorImpl() {
        this.username = props.getProperty("db.username");
        this.password = props.getProperty("db.password");
        this.url = props.getProperty("db.url");
    }

    @Override
    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
