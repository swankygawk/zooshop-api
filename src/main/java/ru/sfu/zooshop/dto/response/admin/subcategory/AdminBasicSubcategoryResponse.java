package ru.sfu.zooshop.dto.response.admin.subcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminBasicSubcategoryResponse {
  private Long id;
  private String name;
  private String slug;
  private String pictureUrl;
  private boolean hidden;
}
