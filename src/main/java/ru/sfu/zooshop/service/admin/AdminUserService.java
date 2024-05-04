package ru.sfu.zooshop.service.admin;

import org.springframework.data.domain.Pageable;
import ru.sfu.zooshop.dto.request.admin.user.UserEmailUpdateRequest;
import ru.sfu.zooshop.dto.request.admin.user.UserProfileUpdateRequest;
import ru.sfu.zooshop.dto.request.admin.role.UserRoleUpdateRequest;
import ru.sfu.zooshop.dto.response.admin.user.AllUsersResponse;
import ru.sfu.zooshop.dto.response.admin.user.RichUserResponse;

public interface AdminUserService {
  AllUsersResponse getUsers(Long authenticatedUserId, Pageable pageable);
  RichUserResponse getUserById(Long authenticatedUserId, Long id);
  void updateUserProfile(Long authenticatedUserId, Long id, UserProfileUpdateRequest request);
  void updateUserEmail(Long authenticatedUserId, Long id, UserEmailUpdateRequest request);
  void deleteUserProfilePicture(Long authenticatedUserId, Long id);
  void toggleUserAccountLock(Long authenticatedUserId, Long id, boolean locked);
  void disableUserMfa(Long authenticatedUserId, Long id);
  void updateUserRole(Long authenticatedUserId, Long id, UserRoleUpdateRequest request);
}
