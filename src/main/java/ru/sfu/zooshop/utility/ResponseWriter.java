package ru.sfu.zooshop.utility;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.exception.ApiException;

import java.io.OutputStream;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.sfu.zooshop.utility.ResponseUtility.getExceptionResponse;

@RequiredArgsConstructor
@Slf4j
@Component
public class ResponseWriter {
  private final ObjectWriter objectWriter;

  public void write(HttpServletResponse response, HttpStatus status, Response body) {
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(status.value());
    try {
      OutputStream out = response.getOutputStream();
      this.objectWriter.writeValue(out, body);
      out.flush();
    } catch (Exception exception) {
      log.error("An error occurred while writing response: {}", exception.getMessage());
      throw new ApiException(exception.getMessage());
    }
  }

  public void writeExceptionResponse(HttpServletRequest request, HttpServletResponse response, Exception exception) {
    HttpStatus status;
    if (exception instanceof AccessDeniedException
      || exception instanceof AccountStatusException) {
      status = FORBIDDEN;
    } else if (exception instanceof InsufficientAuthenticationException
      || exception instanceof AuthenticationCredentialsNotFoundException) {
      status = UNAUTHORIZED;
    } else if (exception instanceof MismatchedInputException
      || exception instanceof UsernameNotFoundException
      || exception instanceof BadCredentialsException) {
      status = BAD_REQUEST;
    } else if (exception instanceof ApiException) {
      status = ((ApiException) exception).getStatus();
    } else {
      status = INTERNAL_SERVER_ERROR;
    }
    Response exceptionResponse = getExceptionResponse(request, status, exception);
    this.write(response, status, exceptionResponse);
  }
}
