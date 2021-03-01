package org.poliakov.conferencium.utils;

import org.poliakov.conferencium.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InMemoryConnectionPool implements ConnectionPool {
    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:");
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
