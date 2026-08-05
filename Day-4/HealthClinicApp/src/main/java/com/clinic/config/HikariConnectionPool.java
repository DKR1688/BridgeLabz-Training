package com.clinic.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class HikariConnectionPool {
    private static final HikariDataSource DATA_SOURCE;
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv().getOrDefault("DB_URL",
                "jdbc:mysql://localhost:3306/health_clinic_db?useSSL=false&allowPublicKeyRetrieval=true&connectionTimeZone=%2B05:30&forceConnectionTimeZoneToSession=true"));
        config.setUsername(System.getenv().getOrDefault("DB_USERNAME", "root"));
        config.setPassword(System.getenv().getOrDefault("DB_PASSWORD", "Root@1234"));
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setPoolName("health-clinic-pool");
        DATA_SOURCE = new HikariDataSource(config);
    }

    private HikariConnectionPool() {
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

    public static void closePool() {
        DATA_SOURCE.close();
    }
}
