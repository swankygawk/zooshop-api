package ru.sfu.zooshop.configuration;

import com.github.slugify.Slugify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlugifierConfiguration {
  private static final Slugify slugifier = Slugify.builder()
    .lowerCase(false)
    .transliterator(true)
    .build();

  @Bean
  public static Slugify slugifier() {
    return slugifier;
  }
}
