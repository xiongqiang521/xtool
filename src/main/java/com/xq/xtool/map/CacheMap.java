package com.xq.xtool.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Description:
 * 最大容量
 * 最大缓存时间
 * 延迟执行
 *
 * @author 13797
 * @version v0.0.1
 * 2021/11/2 22:32
 */
public class CacheMap<T> {
    private final Map<String, T> CACHE = new ConcurrentHashMap<>();

    // 定时任务清理
    private long delay;
    private TimeUnit timeUnit;
    private Function<String, T> function;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    public CacheMap(long delay, TimeUnit timeUnit, Function<String, T> function) {
        this.delay = delay;
        this.timeUnit = timeUnit;
        this.function = function;
    }

    private T put(String key) {
        // 定时删除
        if (delay > 0) {
            executor.schedule(() -> CACHE.remove(key), delay, timeUnit);
        }
        return function.apply(key);
    }

    public T get(String key) {
        return CACHE.computeIfAbsent(key, this::put);
    }

    public Map<String, T> getMap() {
        return new HashMap<>(CACHE);
    }

    public static class Builder {
        private long delay;
        private TimeUnit timeUnit;
        private Function<String, ?> function;

        public static Builder newMap() {
            return new Builder();
        }

        public Builder maxTime(long delay, TimeUnit timeUnit) {
            this.delay = delay;
            this.timeUnit = timeUnit;
            return this;
        }

        public <T> CacheMap<T> build(Function<String, T> function) {
            if (function == null) {
                throw new IllegalArgumentException();
            }
            this.function = function;
            return new CacheMap<T>(delay, timeUnit, function);
        }

    }

}
