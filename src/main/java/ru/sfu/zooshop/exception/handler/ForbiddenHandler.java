package ru.sfu.zooshop.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.sfu.zooshop.utility.ResponseWriter;

@RequiredArgsConstructor
@Component
public class ForbiddenHandler implements AccessDeniedHandler {
  private final ResponseWriter responseWriter;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
    this.responseWriter.writeExceptionResponse(request, response, exception);
  }
}
