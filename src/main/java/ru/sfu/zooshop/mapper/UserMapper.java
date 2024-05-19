package ru.sfu.zooshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sfu.zooshop.configuration.MapstructMapperConfiguration;
import ru.sfu.zooshop.dto.response.user.BasicAuditorResponse;
import ru.sfu.zooshop.dto.response.user.address.AddressResponse;
import ru.sfu.zooshop.dto.response.user.product.ShortProductResponse;
import ru.sfu.zooshop.dto.response.user.review.BasicReviewResponse;
import ru.sfu.zooshop.dto.response.user.review.RichReviewResponse;
import ru.sfu.zooshop.dto.response.user.user.ProfileResponse;
import ru.sfu.zooshop.entity.AddressEntity;
import ru.sfu.zooshop.entity.ProductEntity;
import ru.sfu.zooshop.entity.ReviewEntity;
import ru.sfu.zooshop.entity.UserEntity;

import static ru.sfu.zooshop.constant.Constant.DEFAULT_PROFILE_PICTURE_URL;

@Mapper(config = MapstructMapperConfiguration.class)
public interface UserMapper extends Default {
  // User
  @Mapping(
    source = "profilePictureUrl",
    target = "profilePictureUrl",
    defaultValue = DEFAULT_PROFILE_PICTURE_URL
  )
  BasicAuditorResponse userEntityToBasicAuditorResponse(UserEntity userEntity);

  @Mapping(
    source = "credential.expirationTime",
    target = "credential.expiresAt"
  )
  @Mapping(
    source = "profilePictureUrl",
    target = "profilePictureUrl",
    defaultValue = DEFAULT_PROFILE_PICTURE_URL
  )
  ProfileResponse userEntityToProfileResponse(UserEntity userEntity);

  // Address
  AddressResponse addressEntityToAddressResponse(AddressEntity addressEntity);

  // Product
  ShortProductResponse productEntityToShortProductResponse(ProductEntity productEntity);

  // FavoriteProduct

  // Order

  // Review
  BasicReviewResponse reviewEntityToBasicReviewResponse(ReviewEntity reviewEntity);

  RichReviewResponse reviewEntityToRichReviewResponse(ReviewEntity reviewEntity);
}
