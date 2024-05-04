package ru.sfu.zooshop.dto.response.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserAuditResponse {
  protected BasicAuditorResponse createdBy;
  protected BasicAuditorResponse updatedBy;
  protected String createdAt;
  protected String updatedAt;
}
