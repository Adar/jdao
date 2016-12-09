package co.ecso.dacato.h2;

import co.ecso.dacato.config.ApplicationConfig;
import co.ecso.dacato.connection.ConnectionPool;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HTwoTestApplicationConfig.
 *
 * @author Christian Senkowski (cs@2scale.net)
 * @version $Id:$
 * @since 06.09.16
 */
final class HTwoTestApplicationConfig implements ApplicationConfig {
    private static volatile snaq.db.ConnectionPool connectionPool = null;
    private static volatile ExecutorService threadPool = null;

    @Override
    public String databasePoolName() {
        return "dbpoolhtwo";
    }

    @Override
    public int databasePoolMin() {
        return 1;
    }

    @Override
    public int databasePoolMax() {
        return 10;
    }

    @Override
    public int databasePoolMaxSize() {
        return 100;
    }

    @Override
    public long databasePoolIdleTimeout() {
        return 1000;
    }

    @Override
    public String connectionString() {
        return "jdbc:h2:tcp://localhost/~/test";
    }

    @Override
    public ExecutorService threadPool() {
        if (threadPool == null) {
            threadPool = Executors.newCachedThreadPool();

        }
        return threadPool;
    }

    @Override
    public synchronized ConnectionPool<Connection> databaseConnectionPool() {
        if (connectionPool == null) {
            connectionPool = new snaq.db.ConnectionPool(databasePoolName(), databasePoolMin(),
                    databasePoolMax(), databasePoolMaxSize(), databasePoolIdleTimeout(),
                    connectionString(), null);
        }
        return () -> connectionPool.getConnection();
    }

}
