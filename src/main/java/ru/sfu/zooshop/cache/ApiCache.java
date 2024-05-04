package ru.sfu.zooshop.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ApiCache<K, V> {
  private final Cache<K, V> cache; // TODO change cache and add signed up users cache

  public ApiCache(Integer expirationTime, TimeUnit timeUnit) {
    cache = CacheBuilder.newBuilder()
      .expireAfterWrite(expirationTime, timeUnit)
      .concurrencyLevel(Runtime.getRuntime().availableProcessors())
      .build();
  }

  public V get(@NotNull K key) {
    log.info("Getting from Cache with key {}", key.toString());
    return cache.getIfPresent(key);
  }

  public void put(@NotNull K key, @NotNull V value) {
    log.info("Putting to Cache with key {}", key.toString());
    cache.put(key, value);
  }

  public void invalidate(@NotNull K key) {
    log.info("Invalidating in Cache with key {}", key.toString());
    cache.invalidate(key);
  }
}
