package com.clinic;

import com.clinic.config.HikariConnectionPool;
import com.clinic.ui.ConsoleMenu;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

public class Main {
    public static void main(String[] args) {
        try {
            new ConsoleMenu().start();
        } finally {
            HikariConnectionPool.closePool();
            AbandonedConnectionCleanupThread.checkedShutdown();
        }
    }
}
