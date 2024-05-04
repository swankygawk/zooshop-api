package ru.sfu.zooshop.dto.response.admin;

import lombok.*;
import ru.sfu.zooshop.dto.response.admin.user.BasicUserResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AdminAuditResponse {
  private BasicUserResponse createdBy;
  private BasicUserResponse updatedBy;
  private String createdAt;
  private String updatedAt;
}
