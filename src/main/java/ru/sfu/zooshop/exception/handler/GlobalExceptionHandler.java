package ru.sfu.zooshop.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.sfu.zooshop.dto.response.ValidationFailureResponse;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.SystemMapper;

import java.util.List;

import static org.springframework.http.HttpStatus.valueOf;
import static ru.sfu.zooshop.utility.ResponseUtility.getExceptionResponse;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements ErrorController {
  private final HttpServletRequest request;
  private final SystemMapper mapper;

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
    Exception exception,
    Object body,
    @NonNull HttpHeaders headers,
    @NonNull HttpStatusCode statusCode,
    @NonNull WebRequest webRequest
  ) {
    log.error("handleExceptionInternal: {}", exception.getMessage());

    return new ResponseEntity<>(
      getResponse(
        request,
        valueOf(statusCode.value()),
        exception.getMessage(),
        null
      ),
      headers,
      statusCode
    );
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException exception,
    @NonNull HttpHeaders headers,
    @NonNull HttpStatusCode statusCode,
    @NonNull WebRequest webRequest
  ) {
    log.error("handleMethodArgumentNotValid: {}", exception.getMessage());

    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    List<ValidationFailureResponse> data = fieldErrors
      .stream()
      .map(mapper::fieldErrorToValidationFailureResponse)
      .toList();

    return new ResponseEntity<>(
      getResponse(
        request,
        valueOf(statusCode.value()),
        "Invalid request body. See 'data' for more information",
        data
      ),
      headers,
      statusCode
    );
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<Response> handleApiException(ApiException exception) {
    log.error("handleApiException: {}", exception.getMessage());

    return new ResponseEntity<>(
      getResponse(
        request,
        exception.getStatus(),
        exception.getMessage(),
        null
      ),
      exception.getStatus()
    );
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Response> handleBadCredentialsException(BadCredentialsException exception, HttpStatusCode statusCode) {
    log.error("handleBadCredentialsException: {}", exception.getMessage());
    return new ResponseEntity<>(
      getExceptionResponse(
        request,
        valueOf(statusCode.value()),
        exception
      ),
      statusCode
    );
  }

  // todo
}
