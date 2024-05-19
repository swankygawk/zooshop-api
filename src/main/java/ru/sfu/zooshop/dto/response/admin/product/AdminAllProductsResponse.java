package ru.sfu.zooshop.dto.response.admin.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAllProductsResponse {
  private Page<AdminBasicProductResponse> products;
}
