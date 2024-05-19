package ru.sfu.zooshop.dto.response.admin.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.admin.AdminAuditResponse;
import ru.sfu.zooshop.dto.response.admin.category.AdminBasicCategoryResponse;
import ru.sfu.zooshop.dto.response.admin.productpicture.AdminRichProductPictureResponse;
import ru.sfu.zooshop.dto.response.admin.subcategory.AdminBasicSubcategoryResponse;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRichProductResponse extends AdminAuditResponse {
  private Long id;
  private AdminBasicCategoryResponse category;
  private AdminBasicSubcategoryResponse subcategory;
  private String name;
  private String slug;
  private String description;
  private List<AdminRichProductPictureResponse> pictures;
  private BigDecimal price;
  private Integer quantityInStock;
  private boolean inStock;
  private Float averageRating;
  private Integer popularity;
  private boolean hidden;
}
