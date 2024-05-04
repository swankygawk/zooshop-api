package ru.sfu.zooshop.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.sfu.zooshop.utility.ResponseWriter;

@RequiredArgsConstructor
@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
  private final ResponseWriter responseWriter;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
    this.responseWriter.writeExceptionResponse(request, response, exception);
  }
}
