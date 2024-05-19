package ru.sfu.zooshop.dto.response.open;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicAuditorResponse {
  private String fullName;
  private String profilePictureUrl;
}
