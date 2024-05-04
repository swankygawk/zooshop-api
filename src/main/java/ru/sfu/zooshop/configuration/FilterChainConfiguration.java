package ru.sfu.zooshop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.sfu.zooshop.exception.handler.ForbiddenHandler;
import ru.sfu.zooshop.exception.handler.UnauthorizedHandler;
import ru.sfu.zooshop.security.ApiHttpConfigurer;

import static com.google.common.net.HttpHeaders.*;
import static java.util.List.of;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static ru.sfu.zooshop.constant.Constant.BASE_PATH;

@RequiredArgsConstructor
@Configuration
public class FilterChainConfiguration {
  private final ForbiddenHandler forbiddenHandler;
  private final UnauthorizedHandler unauthorizedHandler;
  private final ApiHttpConfigurer apiHttpConfigurer;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedOrigins(of("http://localhost:8080", "http://localhost:3000")); // Spring Backend Application & React Frontend Application
    corsConfiguration.setAllowedHeaders(of(ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN, CONTENT_TYPE, ACCEPT, X_REQUESTED_WITH, ACCESS_CONTROL_REQUEST_HEADERS, ACCESS_CONTROL_REQUEST_METHOD, ACCESS_CONTROL_ALLOW_CREDENTIALS));
    corsConfiguration.setExposedHeaders(of(ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN, CONTENT_TYPE, ACCEPT, X_REQUESTED_WITH, ACCESS_CONTROL_REQUEST_HEADERS, ACCESS_CONTROL_REQUEST_METHOD, ACCESS_CONTROL_ALLOW_CREDENTIALS));
    corsConfiguration.setAllowedMethods(of(GET.name(), POST.name(), PUT.name(), PATCH.name(), DELETE.name(), OPTIONS.name()));
    corsConfiguration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(BASE_PATH, corsConfiguration);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .anonymous(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .cors(
        cors -> cors
          .configurationSource(corsConfigurationSource())
      )
      .sessionManagement(
        session -> session
          .sessionCreationPolicy(STATELESS)
      )
      .exceptionHandling(
        exception -> exception
          .authenticationEntryPoint(unauthorizedHandler)
          .accessDeniedHandler(forbiddenHandler)
      )
      .logout(AbstractHttpConfigurer::disable) // we are using custom ApiSignOutFilter
      .authorizeHttpRequests(
        request -> request
          .requestMatchers(OPTIONS).permitAll()
          .requestMatchers("/auth/**").permitAll()
          .anyRequest().authenticated()
      )
      .with(apiHttpConfigurer, withDefaults());
    return http.build();
  }
}
