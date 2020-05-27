package ru.students.lab.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DatabaseConfigurer {

    private static final Logger LOG = LogManager.getLogger(DatabaseConfigurer.class);
    private static final String DB_PROPS_FILE = "db.properties";
    private final Map<String, String> config;
    private final Scanner sc;
    private Connection dbConnection = null;

    public DatabaseConfigurer() {
        sc = new Scanner(System.in);
        config = new LinkedHashMap<>();
        config.put("url", "");
        config.put("user", "");
        config.put("password", "");
    }

    public void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB_PROPS_FILE)) {
            Properties dbProps = new Properties();
            dbProps.load(inputStream);
            setConfig(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"));

            System.out.println("Database properties have been loaded from resource 'db.properties'");
            LOG.info("Database properties have been loaded from resource 'db.properties'");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Unable to load default database settings");
            LOG.error("Unable to load default database settings", e);
            System.exit(-1);
        }
    }

    public boolean needReadProperties() {
        System.out.print("Do you want to edit the db config parameters?[y/n]: ");
        String r = sc.nextLine().toLowerCase();
        return r.equals("y") || r.equals("yes");
    }

    public void readCustomProperties() {
        for( String parameter : config.keySet()) {
            String input = "";
            do {
                System.out.print(parameter + ": ");
                input = sc.nextLine();
                input = input.isEmpty() ? null : input;
            } while (input == null);
            config.replace(parameter, input);
        }
        sc.close();
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

    public void setConfig(String url, String user, String pass) {
        this.config.put("url", url);
        this.config.put("user", user);
        this.config.put("password", pass);
    }

    public void disconnect() {
        LOG.info("Disconnecting the database...");
        System.out.println("Disconnecting the database...");
        try {
            dbConnection.close();
        } catch (SQLException ex) {
            System.err.println("error disconnecting the database, check the logs");
            LOG.error("error disconnecting the database", ex);
        }
    }

    public Map<String, String> getConfig() {
        return config;
    }
    public String getDBUrl() {
        return config.get("url");
    }
    public String getDBUser() {
        return config.get("user");
    }
    public String getDBPass() {
        return config.get("password");
    }
    public Connection getDbConnection() {
        return dbConnection;
    }
}
