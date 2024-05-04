package ru.sfu.zooshop.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final ObjectReader objectReader = objectMapper.reader();
  private static final ObjectWriter objectWriter = objectMapper.writer();

  @Bean
  public static ObjectMapper objectMapper() {
    return objectMapper;
  }

  @Bean
  public static ObjectReader objectReader() {
    return objectReader;
  }

  @Bean
  public static ObjectWriter objectWriter() {
    return objectWriter;
  }
}
