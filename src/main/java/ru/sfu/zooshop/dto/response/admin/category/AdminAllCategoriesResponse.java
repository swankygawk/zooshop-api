package ru.sfu.zooshop.dto.response.admin.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAllCategoriesResponse {
  private List<AdminBasicCategoryResponse> categories;
}
