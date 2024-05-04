package ru.sfu.zooshop.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.sfu.zooshop.security.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
  private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return passwordEncoder;
  }

  @Bean
  FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
    FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(jwtFilter);
    registration.setEnabled(false);
    return registration;
  }
}
