package org.poliakov.conferencium.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool connectionPool = null;
    private DataSource dataSource = null;

    private ConnectionPool() {
        LOGGER.info("Starting ConnectionPool");
    }

    public Connection getConnection() {
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/conferencium");
            c = ds.getConnection();
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return c;
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            TimeZone timeZone = TimeZone.getTimeZone("Europe/Kiev");
            TimeZone.setDefault(timeZone);
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }
}
