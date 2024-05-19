package ru.sfu.zooshop.dto.request.admin.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplyRequest {
  @NotNull(message = "Amount must not be null")
  @Positive(message = "Amount must be greater than 0")
  private Integer amount;
}
