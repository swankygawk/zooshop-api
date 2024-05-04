package ru.sfu.zooshop.security;

import com.fasterxml.jackson.databind.ObjectReader;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CorsFilter;
import ru.sfu.zooshop.service.JwtService;
import ru.sfu.zooshop.utility.ResponseWriter;

@RequiredArgsConstructor
@Component
public class ApiHttpConfigurer extends AbstractHttpConfigurer<ApiHttpConfigurer, HttpSecurity> {
  private final ApiAuthenticationProvider authenticationProvider;
  private final JwtFilter jwtFilter;
  private final ApiSignOutFilter apiSignOutFilter;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtService jwtService;
  private final ObjectReader objectReader;
  private final ResponseWriter responseWriter;
  private final Validator validator;

  @Override
  public void init(HttpSecurity http) {
    http.authenticationProvider(authenticationProvider);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.addFilterAfter(
      this.jwtFilter,
      CorsFilter.class
    );
    http.addFilterAfter(
      this.apiSignOutFilter,
      JwtFilter.class
    );
    http.addFilterAfter(
      new ApiAuthenticationFilter(this.authenticationConfiguration.getAuthenticationManager(), this.jwtService, this.objectReader, this.responseWriter, this.validator),
      ApiSignOutFilter.class
    );
  }
}
