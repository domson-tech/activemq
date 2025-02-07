package org.apache.activemq.store.kahadb.cache;

import org.apache.activemq.util.LFUCache;
import org.apache.activemq.util.LRUCache;

import java.util.Map;

/**
 * build mode
 * build cache according to cache type
 * @param <K>
 * @param <V>
 */
public class CacheBuilder<K, V> {

    private int cacheSize;

    private int lrukThresholdValue;

    private CacheType cacheType;

    private float lfuEvictionFactor = 0.2f;

    private float lruLoadFactor;

    private int maxCacheSize;

    private boolean accessOrder;

    public CacheBuilder setCacheType(CacheType cacheType){
        this.cacheType = cacheType;
        return this;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public CacheBuilder setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }

    public CacheBuilder setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
        return this;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public CacheBuilder setLrukThresholdValue(int lrukThresholdValue) {
        this.lrukThresholdValue = lrukThresholdValue;
        return this;
    }

    public int getLrukThresholdValue() {
        return lrukThresholdValue;
    }

    public CacheBuilder setLfuEvictionFactor(float lfuEvictionFactor) {
        this.lfuEvictionFactor = lfuEvictionFactor;
        return this;
    }

    public CacheBuilder setLruLoadFactor(float lruLoadFactor) {
        this.lruLoadFactor = lruLoadFactor;
        return this;
    }

    public CacheBuilder setAccessOrder(boolean accessOrder) {
        this.accessOrder = accessOrder;
        return this;
    }

    public Map<K, V> build(){
        Map<K, V> cache = null;
        switch (cacheType){
            case LURK:
                cache = new LRUKCache<>(this.cacheSize, this.lrukThresholdValue);
                break;
            case LFU:
                cache = new LFUCache<>(this.maxCacheSize, lfuEvictionFactor);
                break;
            default:
                cache = new LRUCache<>(cacheSize, maxCacheSize, lruLoadFactor, accessOrder);
        }

        return cache;
    }

}
