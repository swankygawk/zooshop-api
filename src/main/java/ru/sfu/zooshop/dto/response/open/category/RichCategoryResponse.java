package ru.sfu.zooshop.dto.response.open.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.domain.Page;
import ru.sfu.zooshop.dto.response.open.product.BasicProductResponse;
import ru.sfu.zooshop.dto.response.open.subcategory.BasicSubcategoryResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RichCategoryResponse {
  private Long id;
  private String name;
  private String slug;
  private String pictureUrl;
  private List<BasicSubcategoryResponse> children;
  private Page<BasicProductResponse> products;
}
