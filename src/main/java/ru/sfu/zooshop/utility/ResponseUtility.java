package ru.sfu.zooshop.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.exception.ApiException;

import static java.time.OffsetDateTime.now;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


public final class ResponseUtility {
  private static String getExceptionMessage(Exception exception, HttpStatus status) {
    if (exception instanceof UsernameNotFoundException
      || exception instanceof BadCredentialsException
      || exception instanceof AccountStatusException
      || exception instanceof ApiException) return exception.getMessage();
    if (status.isSameCodeAs(FORBIDDEN)) return "You do not have required authority";
    if (status.isSameCodeAs(UNAUTHORIZED)) return "You are not signed in";
    if (status.is5xxServerError()) return "An internal server error occurred";
    return "An error occurred. Please try again";
  }

  public static Response getResponse(
    HttpServletRequest request,
    HttpStatus status,
    String message,
    Object data
  ) {
    return new Response(
      now().toString(),
      request.getRequestURI(),
      status.value(),
      status.getReasonPhrase(),
      message,
      data
    );
  }

  public static Response getExceptionResponse(
    HttpServletRequest request,
    HttpStatus status,
    Exception exception
  ) {
    return getResponse(
      request,
      status,
      getExceptionMessage(exception, status),
      null
    );
  }
}
