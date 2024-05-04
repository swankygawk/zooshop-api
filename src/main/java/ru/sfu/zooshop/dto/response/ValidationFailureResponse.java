package ru.sfu.zooshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationFailureResponse {
  private String field;
  private Object rejectedValue;
  private String message;
}
