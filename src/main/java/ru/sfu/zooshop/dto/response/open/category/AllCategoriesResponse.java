package ru.sfu.zooshop.dto.response.open.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllCategoriesResponse {
  private List<BasicCategoryResponse> categories;
}
