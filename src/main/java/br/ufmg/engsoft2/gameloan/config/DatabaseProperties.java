package br.ufmg.engsoft2.gameloan.config;

public class DatabaseProperties {

    private static DatabaseProperties instance;

    private final String databaseUser;
    private final String databaseUrl;

    private final String databasePassword;

    private final String databaseName;

    private DatabaseProperties() {
        databaseUser = System.getProperty("database.user");
        databaseUrl = System.getProperty("database.url");
        databasePassword = System.getProperty("database.password");
        databaseName = System.getProperty("database.name");
    }

    public static DatabaseProperties getInstance() {
        if (instance == null) {
            instance = new DatabaseProperties();
        }
        return instance;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

}
