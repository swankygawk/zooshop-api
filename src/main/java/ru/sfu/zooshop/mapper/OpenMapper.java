package ru.sfu.zooshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sfu.zooshop.configuration.MapstructMapperConfiguration;
import ru.sfu.zooshop.dto.response.open.BasicAuditorResponse;
import ru.sfu.zooshop.dto.response.open.category.BasicCategoryResponse;
import ru.sfu.zooshop.dto.response.open.category.RichCategoryResponse;
import ru.sfu.zooshop.dto.response.open.product.BasicProductResponse;
import ru.sfu.zooshop.dto.response.open.product.RichProductResponse;
import ru.sfu.zooshop.dto.response.open.productpicture.ProductPictureResponse;
import ru.sfu.zooshop.dto.response.open.review.BasicReviewResponse;
import ru.sfu.zooshop.dto.response.open.subcategory.BasicSubcategoryResponse;
import ru.sfu.zooshop.dto.response.open.subcategory.RichSubcategoryResponse;
import ru.sfu.zooshop.entity.*;

import static ru.sfu.zooshop.constant.Constant.*;

@Mapper(config = MapstructMapperConfiguration.class)
public interface OpenMapper extends Default {
  // User
  @Mapping(
    source = "profilePictureUrl",
    target = "profilePictureUrl",
    defaultValue = DEFAULT_PROFILE_PICTURE_URL
  )
  BasicAuditorResponse userEntityToBasicAuditorResponse(UserEntity userEntity);

  // Category
  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_CATEGORY_PICTURE_URL
  )
  BasicCategoryResponse categoryEntityToBasicCategoryResponse(CategoryEntity categoryEntity);

  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_CATEGORY_PICTURE_URL
  )
  @Mapping(
    target = "products",
    ignore = true
  )
  RichCategoryResponse categoryEntityToRichCategoryResponse(CategoryEntity categoryEntity);

  // Subcategory
  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_SUBCATEGORY_PICTURE_URL
  )
  BasicSubcategoryResponse subcategoryEntityToBasicSubcategoryResponse(SubcategoryEntity subcategoryEntity);

  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_SUBCATEGORY_PICTURE_URL
  )
  @Mapping(
    target = "products",
    ignore = true
  )
  RichSubcategoryResponse subcategoryEntityToRichSubcategoryResponse(SubcategoryEntity subcategoryEntity);

  // Product
  BasicProductResponse productEntityToBasicProductResponse(ProductEntity productEntity);

  RichProductResponse productEntityToRichProductResponse(ProductEntity productEntity);

  // Product Picture
  ProductPictureResponse productPictureEntityToProductPictureResponse(ProductPictureEntity productPictureEntity);

  // Review
  BasicReviewResponse reviewEntityToBasicReviewResponse(ReviewEntity reviewEntity);
}
