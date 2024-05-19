package ru.sfu.zooshop.dto.response.open.subcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import ru.sfu.zooshop.dto.response.open.category.BasicCategoryResponse;
import ru.sfu.zooshop.dto.response.open.product.BasicProductResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RichSubcategoryResponse {
  private Long id;
  private BasicCategoryResponse parent;
  private String name;
  private String slug;
  private String pictureUrl;
  private Page<BasicProductResponse> products;
}
