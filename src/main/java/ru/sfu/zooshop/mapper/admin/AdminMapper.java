package ru.sfu.zooshop.mapper.admin;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sfu.zooshop.configuration.MapstructMapperConfiguration;
import ru.sfu.zooshop.dto.response.admin.authority.AuthorityResponse;
import ru.sfu.zooshop.dto.response.admin.user.*;
import ru.sfu.zooshop.dto.response.admin.role.BasicRoleResponse;
import ru.sfu.zooshop.dto.response.admin.role.RichRoleResponse;
import ru.sfu.zooshop.entity.AuthorityEntity;
import ru.sfu.zooshop.entity.RoleEntity;
import ru.sfu.zooshop.entity.UserEntity;

import static ru.sfu.zooshop.constant.Constant.DEFAULT_PROFILE_PICTURE_URL;

@Mapper(config = MapstructMapperConfiguration.class)
public interface AdminMapper extends AdminDefault {
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
}
