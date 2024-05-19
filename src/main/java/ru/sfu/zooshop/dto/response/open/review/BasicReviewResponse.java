package ru.sfu.zooshop.dto.response.open.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.open.AuditResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicReviewResponse extends AuditResponse {
  private Short rating;
  private String comment;
}
