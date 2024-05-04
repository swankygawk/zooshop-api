package ru.sfu.zooshop.dto.response.admin.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.admin.AdminAuditResponse;
import ru.sfu.zooshop.dto.response.admin.credential.UserCredentialResponse;
import ru.sfu.zooshop.dto.response.admin.role.UserRoleResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RichUserResponse extends AdminAuditResponse {
  private Long id;
  private String referenceId;
  private String userId;
  private String email;
  private UserCredentialResponse credential;
  private String firstName;
  private String lastName;
  private String phone;
  private String profilePictureUrl;
  private String lastSignIn;
  private boolean enabled;
  private boolean accountLocked;
  private boolean mfaEnabled;
  private UserRoleResponse role;
}
