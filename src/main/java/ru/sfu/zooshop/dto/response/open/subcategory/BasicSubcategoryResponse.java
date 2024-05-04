package ru.sfu.zooshop.dto.response.open.subcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicSubcategoryResponse {
  private Long id;
  private String name;
  private String slug;
  private String pictureUrl;
}
