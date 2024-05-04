package ru.sfu.zooshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
public class ApiException extends RuntimeException {
  private final HttpStatus status;

  public ApiException() {
    super("An error occurred");
    this.status = INTERNAL_SERVER_ERROR;
  }

  public ApiException(String message) {
    super(message);
    this.status = INTERNAL_SERVER_ERROR;
  }

  public ApiException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }
}
