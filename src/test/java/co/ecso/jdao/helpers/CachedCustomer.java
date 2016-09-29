package co.ecso.jdao.helpers;

import co.ecso.jdao.AbstractTest;
import co.ecso.jdao.config.ApplicationConfig;
import co.ecso.jdao.database.CachedDatabaseEntity;
import co.ecso.jdao.database.ColumnList;
import co.ecso.jdao.database.cache.Cache;
import co.ecso.jdao.database.cache.CacheKey;
import co.ecso.jdao.database.query.DatabaseField;
import co.ecso.jdao.database.query.DatabaseResultField;
import co.ecso.jdao.database.query.SingleColumnQuery;
import co.ecso.jdao.database.query.SingleColumnUpdateQuery;

import java.sql.Types;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CachedCustomer.
 *
 * @author Christian Senkowski (cs@2scale.net)
 * @version $Id:$
 * @since 17.09.16
 */
public final class CachedCustomer implements CachedDatabaseEntity<Long> {

    private static final String TABLE_NAME = "customer";
    private static final String QUERY = String.format("SELECT %%s FROM %s WHERE id = ?", TABLE_NAME);
    private final ApplicationConfig config;
    private final Long id;
    private AtomicBoolean objectValid = new AtomicBoolean(true);

    @SuppressWarnings("WeakerAccess")
    public CachedCustomer(final ApplicationConfig config, final Long id) {
        this.config = config;
        this.id = id;
    }

    @Override
    public ApplicationConfig config() {
        return config;
    }

    @Override
    public Long primaryKey() {
        return id;
    }

    @Override
    public CompletableFuture<CachedCustomer> save(final ColumnList columnValuesToSet) {
        return update(new SingleColumnUpdateQuery<>("UPDATE " + TABLE_NAME + " SET %%s WHERE %s = ?",
                Fields.ID, this.id, columnValuesToSet), () -> objectValid).thenApply(newId ->
                new CachedCustomer(config, Long.valueOf(newId)));
    }

    @Override
    public Cache<CacheKey, CompletableFuture> cache() {
        return AbstractTest.CACHE;
    }

    public CompletableFuture<DatabaseResultField<String>> firstName() {
        return this.findOne(new SingleColumnQuery<>(QUERY, Fields.FIRST_NAME, Fields.ID, this.primaryKey()), () ->
                this.objectValid);
    }

    public CompletableFuture<DatabaseResultField<Long>> number() {
        return this.findOne(new SingleColumnQuery<>(QUERY, Fields.NUMBER, Fields.ID, this.primaryKey()), () ->
                this.objectValid);
    }

    public static final class Fields {
        public static final DatabaseField<String> FIRST_NAME =
                new DatabaseField<>("customer_first_name", String.class, Types.VARCHAR);
        static final DatabaseField<Long> ID = new DatabaseField<>("id", Long.class, Types.BIGINT);
        static final DatabaseField<Long> NUMBER =
                new DatabaseField<>("customer_number", Long.class, Types.BIGINT);
    }
}
