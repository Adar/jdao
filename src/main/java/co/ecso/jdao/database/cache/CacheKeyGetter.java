package co.ecso.jdao.database.cache;

import co.ecso.jdao.database.query.Query;

/**
 * CacheKeyGetter.
 *
 * @author Christian Senkowski (cs@2scale.net)
 * @version $Id:$
 * @since 21.09.16
 */
@FunctionalInterface
interface CacheKeyGetter {

    /**
     * Get cache key.
     *
     * @param <T> Type of Query and CacheKey must match.
     * @param query Query.
     * @return Cache key.
     */
    <T> CacheKey<T> getCacheKey(final Query<T> query);

}