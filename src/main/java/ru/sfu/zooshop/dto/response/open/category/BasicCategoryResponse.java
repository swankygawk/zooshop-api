package ru.sfu.zooshop.dto.response.open.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicCategoryResponse {
  private Long id;
  private String name;
  private String slug;
  private String pictureUrl;
}
