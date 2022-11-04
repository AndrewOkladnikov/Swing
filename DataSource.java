package ru.learning.swing.myLibrary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DataSource {
    private static String url = "jdbc:postgresql://localhost:5432/test_db";
    private static final String user = "postgres";
    private static final String password = "1510kirs";
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMinimumIdle(2);
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}