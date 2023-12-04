package br.ufmg.engsoft2.gameloan.infra.database;

import br.ufmg.engsoft2.gameloan.config.DatabaseProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager onlyInstance;
    private static ConnectionFactory connectionFactory;

    private ConnectionManager(){
        var databaseProperties = DatabaseProperties.getInstance();
        connectionFactory = new PostgreSQLConnectionFactory(
                databaseProperties.getDatabaseUrl(),
                databaseProperties.getDatabaseUser(),
                databaseProperties.getDatabasePassword()
        );
    }

    public static ConnectionManager getInstance(){
        if(onlyInstance == null){
            onlyInstance = new ConnectionManager();
        }

        return onlyInstance;
    }

    public Connection getConnection() throws SQLException {
        return connectionFactory.createConnection();
    }
}
