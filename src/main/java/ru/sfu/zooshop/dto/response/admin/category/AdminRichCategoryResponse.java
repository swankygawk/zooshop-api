package ru.sfu.zooshop.dto.response.admin.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import ru.sfu.zooshop.dto.response.admin.AdminAuditResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminBasicProductResponse;
import ru.sfu.zooshop.dto.response.admin.subcategory.AdminBasicSubcategoryResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRichCategoryResponse extends AdminAuditResponse {
  private Long id;
  private String name;
  private String slug;
  private String pictureUrl;
  private boolean hidden;
  private List<AdminBasicSubcategoryResponse> children;
  private Page<AdminBasicProductResponse> products;
}
