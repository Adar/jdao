package co.ecso.dacato.database.cache;

import co.ecso.dacato.database.query.Updater;
import co.ecso.dacato.database.querywrapper.SingleColumnUpdateQuery;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CachedUpdater.
 *
 * @param <T> Type of update -> type of query.
 * @author Christian Scharmach (cs@e-cs.co)
 * @version $Id:$
 * @since 19.09.16
 */
public interface CachedUpdater<T> extends Updater<T>, CacheGetter {

    @Override
    default CompletableFuture<Integer> update(final SingleColumnUpdateQuery<T> query,
                                              final Callable<AtomicBoolean> validityCheck) {
        cache().keySet().stream()
                .filter(k -> k.hasKey(query.tableName()))
                .forEach(k -> cache().invalidate(k));
        return Updater.super.update(query, validityCheck);
    }
}
