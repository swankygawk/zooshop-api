package ru.sfu.zooshop.service.impl.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.request.admin.user.UserEmailUpdateRequest;
import ru.sfu.zooshop.dto.request.admin.user.UserProfileUpdateRequest;
import ru.sfu.zooshop.dto.request.admin.role.UserRoleUpdateRequest;
import ru.sfu.zooshop.dto.response.admin.user.AllUsersResponse;
import ru.sfu.zooshop.dto.response.admin.user.BasicUserResponse;
import ru.sfu.zooshop.dto.response.admin.user.RichUserResponse;
import ru.sfu.zooshop.entity.RoleEntity;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.admin.AdminMapper;
import ru.sfu.zooshop.repository.UserRepository;
import ru.sfu.zooshop.service.admin.AdminUserService;
import ru.sfu.zooshop.service.EmailService;
import ru.sfu.zooshop.service.FileService;
import ru.sfu.zooshop.service.MfaService;
import ru.sfu.zooshop.service.RoleService;
import ru.sfu.zooshop.service.UserService;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static ru.sfu.zooshop.constant.Constant.*;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AdminUserServiceImpl implements AdminUserService {
  private final UserRepository userRepository;
  private final UserService userService;
  private final MfaService mfaService;
  private final EmailService emailService;
  private final FileService fileService;
  private final RoleService roleService;
  private final AdminMapper userMapper;

  private static void validateId(Long authenticatedUserId, Long id) {
    if (Objects.equals(id, SYSTEM_ID)) throw new ApiException(FORBIDDEN, "Interactions with system user are not allowed");
    if (Objects.equals(id, ADMIN_ID)) throw new ApiException(FORBIDDEN, "Interactions with admin user are not allowed");
    if (Objects.equals(authenticatedUserId, id)) throw new ApiException(FORBIDDEN, "Interactions with yourself are not allowed");
  }

  @Override
  public AllUsersResponse getUsers(Long authenticatedUserId, Pageable pageable) {
    Page<BasicUserResponse> users = userRepository.findAllByIdNotIn(List.of(SYSTEM_ID, ADMIN_ID, authenticatedUserId), pageable)
      .map(userMapper::userEntityToBasicUserResponse);
    return new AllUsersResponse(users);
  }

  @Override
  public RichUserResponse getUserById(Long authenticatedUserId, Long id) {
    validateId(authenticatedUserId, id);
    UserEntity user = userService.findUserById(id);
    return userMapper.userEntityToRichUserResponse(user);
  }

  @Override
  public void updateUserProfile(Long authenticatedUserId, Long id, UserProfileUpdateRequest request) {
    validateId(authenticatedUserId, id);
    UserEntity user = userService.findUserById(id);
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setPhone(request.getPhone());
    userRepository.save(user);
  }

  @Override
  public void updateUserEmail(Long authenticatedUserId, Long id, UserEmailUpdateRequest request) {
    validateId(authenticatedUserId, id);
    emailService.validateEmail(request.getNewEmail());
    UserEntity user = userService.findUserById(id);
    user.setEmail(request.getNewEmail());
    userRepository.save(user);
  }

  @Override
  public void deleteUserProfilePicture(Long authenticatedUserId, Long id) {
    validateId(authenticatedUserId, id);
    UserEntity user = userService.findUserById(id);
    if (user.getProfilePictureUrl() == null) throw new ApiException(BAD_REQUEST, "User does not have a profile picture");
    user.setProfilePictureUrl(null);
    userRepository.save(user);
    fileService.deleteFile(PROFILE_PICTURE_STORAGE_LOCATION, user.getReferenceId() + ".png");
  }

  @Override
  public void toggleUserAccountLock(Long authenticatedUserId, Long id, boolean locked) {
    validateId(authenticatedUserId, id);
    UserEntity user = userService.findUserById(id);
    if (Objects.equals(!user.isAccountNonLocked(), locked)) throw new ApiException(BAD_REQUEST, "User's account is already " + (locked ? "locked" : "unlocked"));
    user.setAccountNonLocked(!locked);
    userRepository.save(user);
  }

  @Override
  public void disableUserMfa(Long authenticatedUserId, Long id) {
    validateId(authenticatedUserId, id);
    UserEntity user = userService.findUserById(id);
    if (!user.isMfaEnabled()) throw new ApiException(BAD_REQUEST, "User's MFA is already disabled");
    user.setMfaSecret(null);
    user.setMfaEnabled(false);
    user = userRepository.save(user);
    mfaService.deleteRecoveryCodes(user);
  }

  @Override
  public void updateUserRole(Long authenticatedUserId, Long id, UserRoleUpdateRequest request) {
    validateId(authenticatedUserId, id);
    if (Objects.equals(request.getId(), SYSTEM_ROLE_ID)) throw new ApiException(FORBIDDEN, "Interactions with system role are not allowed");
    if (Objects.equals(request.getId(), ADMIN_ROLE_ID)) throw new ApiException(FORBIDDEN, "Interactions with admin role are not allowed");

    UserEntity user = userService.findUserById(id);
    RoleEntity role = roleService.findRoleById(request.getId());
    user.setRole(role);
    userRepository.save(user);
  }
}
