package ru.sfu.zooshop.mapper.system;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.validation.FieldError;
import ru.sfu.zooshop.configuration.MapstructMapperConfiguration;
import ru.sfu.zooshop.dto.response.ValidationFailureResponse;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.security.user.AuthenticatedUser;

@Mapper(config = MapstructMapperConfiguration.class)
public interface SystemMapper {
  AuthenticatedUser userEntityToAuthenticatedUser(UserEntity userEntity);

  @Mapping(
    source = "defaultMessage",
    target = "message"
  )
  ValidationFailureResponse fieldErrorToValidationFailureResponse(FieldError fieldError);
}
