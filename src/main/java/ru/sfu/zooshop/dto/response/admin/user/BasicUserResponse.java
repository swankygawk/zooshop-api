package ru.sfu.zooshop.dto.response.admin.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserResponse {
  private Long id;
  private String fullName;
  private String profilePictureUrl;
}
