package ru.students.lab.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfigurer {

    private static final Logger LOG = LogManager.getLogger(DatabaseConfigurer.class);
    private static final String DB_PROPS_FILE = "db.properties";
    private final Properties dbProperties = new Properties();
    private Connection dbConnection = null;

    public void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB_PROPS_FILE)) {

            dbProperties.load(inputStream);
            System.out.println("Database properties have been loaded from resource 'db.properties'");
            LOG.info("Database properties have been loaded from resource 'db.properties'");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Unable to load default database settings");
            LOG.error("Unable to load default database settings", e);
            System.exit(-1);
        }
    }

    public void setConnection() {
        try {
            dbConnection = DriverManager.getConnection(getDBUrl(), getDBUser(), getDBPass());
            System.out.println("DB_driver Successfully connected on: " + getDBUrl());
            LOG.info("DB Connection successfully mounted on: " + getDBUrl());
        } catch (SQLException e) {
            LOG.error("Unable to connect to database", e);
            System.err.println("Unable to connect to database. Check the URL, the username and the password and try again. Check logs for details.");
            System.exit(-1);
        }
    }

    public String getDBUrl() {
        return dbProperties.getProperty("url");
    }
    public String getDBUser() {
        return dbProperties.getProperty("user");
    }
    public String getDBPass() {
        return dbProperties.getProperty("password");
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public void disconnect() {
        LOG.info("Disconnecting the database...");
        System.out.println("Disconnecting the database...");
        try {
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
