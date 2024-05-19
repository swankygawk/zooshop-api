package ru.sfu.zooshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sfu.zooshop.configuration.MapstructMapperConfiguration;
import ru.sfu.zooshop.dto.response.admin.authority.AuthorityResponse;
import ru.sfu.zooshop.dto.response.admin.category.AdminBasicCategoryResponse;
import ru.sfu.zooshop.dto.response.admin.category.AdminRichCategoryResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminBasicProductResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminRichProductResponse;
import ru.sfu.zooshop.dto.response.admin.productpicture.AdminBasicProductPictureResponse;
import ru.sfu.zooshop.dto.response.admin.productpicture.AdminRichProductPictureResponse;
import ru.sfu.zooshop.dto.response.admin.role.BasicRoleResponse;
import ru.sfu.zooshop.dto.response.admin.role.RichRoleResponse;
import ru.sfu.zooshop.dto.response.admin.subcategory.AdminBasicSubcategoryResponse;
import ru.sfu.zooshop.dto.response.admin.subcategory.AdminRichSubcategoryResponse;
import ru.sfu.zooshop.dto.response.admin.user.BasicUserResponse;
import ru.sfu.zooshop.dto.response.admin.user.RichUserResponse;
import ru.sfu.zooshop.entity.*;

import static ru.sfu.zooshop.constant.Constant.*;

@Mapper(config = MapstructMapperConfiguration.class)
public interface AdminMapper extends Default {
  // User
  @Mapping(
    source = "profilePictureUrl",
    target = "profilePictureUrl",
    defaultValue = DEFAULT_PROFILE_PICTURE_URL
  )
  BasicUserResponse userEntityToBasicUserResponse(UserEntity userEntity);

  @Mapping(
    source = "credential.expirationTime",
    target = "credential.expiresAt"
  )
  @Mapping(
    expression = "java(!credentialEntity.isCredentialsNonExpired())",
    target = "credential.expired"
  )
  @Mapping(
    source = "profilePictureUrl",
    target = "profilePictureUrl",
    defaultValue = DEFAULT_PROFILE_PICTURE_URL
  )
  @Mapping(
    expression = "java(!userEntity.isAccountNonLocked())",
    target = "accountLocked"
  )
  RichUserResponse userEntityToRichUserResponse(UserEntity userEntity);

  // Role
  BasicRoleResponse roleEntityToBasicRoleResponse(RoleEntity roleEntity);
  RichRoleResponse roleEntityToRichRoleResponse(RoleEntity roleEntity);

  // Authority
  AuthorityResponse authorityEntityToAuthorityResponse(AuthorityEntity authorityEntity);

  // Category
  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_CATEGORY_PICTURE_URL
  )
  AdminBasicCategoryResponse categoryEntityToAdminBasicCategoryResponse(CategoryEntity categoryEntity);

  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_CATEGORY_PICTURE_URL
  )
  @Mapping(
    target = "products",
    ignore = true
  )
  AdminRichCategoryResponse categoryEntityToAdminRichCategoryResponse(CategoryEntity categoryEntity);

  // Subcategory
  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_SUBCATEGORY_PICTURE_URL
  )
  AdminBasicSubcategoryResponse subcategoryEntityToAdminBasicSubcategoryResponse(SubcategoryEntity subcategoryEntity);

  @Mapping(
    source = "pictureUrl",
    target = "pictureUrl",
    defaultValue = DEFAULT_SUBCATEGORY_PICTURE_URL
  )
  @Mapping(
    target = "products",
    ignore = true
  )
  AdminRichSubcategoryResponse subcategoryEntityToAdminRichSubcategoryResponse(SubcategoryEntity subcategoryEntity);

  // Product
  AdminBasicProductResponse productEntityToAdminBasicProductResponse(ProductEntity productEntity);

  AdminRichProductResponse productEntityToAdminRichProductResponse(ProductEntity productEntity);

  // Product Picture
  AdminBasicProductPictureResponse productPictureEntityToAdminBasicProductPictureResponse(ProductPictureEntity productPictureEntity);

  AdminRichProductPictureResponse productPictureEntityToAdminRichProductResponse(ProductPictureEntity productPictureEntity);
}
