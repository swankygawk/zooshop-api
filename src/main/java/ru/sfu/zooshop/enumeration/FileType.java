package ru.sfu.zooshop.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
  PROFILE_PICTURE("/picture/profile/"),
  CATEGORY_PICTURE("/picture/category/"),
  SUBCATEGORY_PICTURE("/picture/subcategory/"),
  PRODUCT_PICTURE("/picture/product/");

  private final String locationOnServer;
}
