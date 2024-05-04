package ru.sfu.zooshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sfu.zooshop.cache.ApiCache;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

  @Bean
  public ApiCache<String, Integer> userCache() {
    return new ApiCache<>(15, TimeUnit.MINUTES);
  }
}
