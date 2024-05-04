package ru.sfu.zooshop.dto.response.user.user;

import lombok.*;
import ru.sfu.zooshop.dto.response.user.UserAuditResponse;
import ru.sfu.zooshop.dto.response.user.credential.ProfileCredentialResponse;
import ru.sfu.zooshop.dto.response.user.role.ProfileRoleResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse extends UserAuditResponse {
  private String userId;
  private String email;
  private ProfileCredentialResponse credential;
  private String firstName;
  private String lastName;
  private String phone;
  private String profilePictureUrl;
  private String lastSignIn;
  private boolean mfaEnabled;
  private ProfileRoleResponse role;
}
