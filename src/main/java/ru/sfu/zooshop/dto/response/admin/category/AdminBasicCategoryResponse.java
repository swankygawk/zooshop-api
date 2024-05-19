package ru.sfu.zooshop.dto.response.admin.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminBasicCategoryResponse {
  private Long id;
  private String name;
  private String slug;
  private String pictureUrl;
  private boolean hidden;
}
