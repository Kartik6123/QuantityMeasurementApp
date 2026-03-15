package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;

    private final List<Connection> available = new ArrayList<>();
    private final List<Connection> inUse     = new ArrayList<>();
    private final String url;
    private final String username;
    private final String password;
    private final int    poolSize;

    private ConnectionPool() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        this.url      = config.getDbUrl();
        this.username = config.getDbUsername();
        this.password = config.getDbPassword();
        this.poolSize = config.getPoolSize();
        initializePool();
    }

    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    private void initializePool() {
        try {
            for (int i = 0; i < poolSize; i++) {
                available.add(DriverManager.getConnection(url, username, password));
            }
            logger.info("ConnectionPool initialized with {} connections", poolSize);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to initialize connection pool", e);
        }
    }

    public synchronized Connection acquireConnection() {
        if (available.isEmpty())
            throw new DatabaseException("Connection pool exhausted");
        Connection conn = available.remove(available.size() - 1);
        inUse.add(conn);
        return conn;
    }

    public synchronized void releaseConnection(Connection conn) {
        inUse.remove(conn);
        available.add(conn);
    }

    public String getStatistics() {
        return "Pool[available=" + available.size() + ", inUse=" + inUse.size() + ", total=" + poolSize + "]";
    }

    public void closeAll() {
        for (Connection c : available) { try { c.close(); } catch (SQLException ignored) {} }
        for (Connection c : inUse)     { try { c.close(); } catch (SQLException ignored) {} }
        available.clear();
        inUse.clear();
        logger.info("ConnectionPool closed");
    }
}
