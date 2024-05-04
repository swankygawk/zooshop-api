package ru.sfu.zooshop.dto.response.admin.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllUsersResponse {
  private Page<BasicUserResponse> users;
}
