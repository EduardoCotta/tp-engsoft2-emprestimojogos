package br.ufmg.engsoft2.gameloan.infra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnectionFactory implements ConnectionFactory {
    private final String url;

    private final String user;

    private final String password;

    public PostgreSQLConnectionFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
