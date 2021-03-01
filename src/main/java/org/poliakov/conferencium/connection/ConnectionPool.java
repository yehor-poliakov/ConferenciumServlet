package org.poliakov.conferencium.connection;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();
}
