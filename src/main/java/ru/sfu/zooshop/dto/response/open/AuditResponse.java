package ru.sfu.zooshop.dto.response.open;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditResponse {
  protected BasicAuditorResponse createdBy;
  protected BasicAuditorResponse updatedBy;
  protected String createdAt;
  protected String updatedAt;
}
