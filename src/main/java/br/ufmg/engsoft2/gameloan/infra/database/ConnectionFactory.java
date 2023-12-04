package br.ufmg.engsoft2.gameloan.infra.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection createConnection() throws SQLException;
}
