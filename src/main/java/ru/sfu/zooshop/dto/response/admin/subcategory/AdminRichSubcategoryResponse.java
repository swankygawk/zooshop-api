package ru.sfu.zooshop.dto.response.admin.subcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import ru.sfu.zooshop.dto.response.admin.AdminAuditResponse;
import ru.sfu.zooshop.dto.response.admin.category.AdminBasicCategoryResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminBasicProductResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRichSubcategoryResponse extends AdminAuditResponse {
  private Long id;
  private AdminBasicCategoryResponse parent;
  private String name;
  private String slug;
  private String pictureUrl;
  private boolean hidden;
  private Page<AdminBasicProductResponse> products;
}
