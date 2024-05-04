package ru.sfu.zooshop.service;

import ru.sfu.zooshop.dto.response.admin.authority.AllAuthoritiesResponse;
import ru.sfu.zooshop.entity.AuthorityEntity;

public interface AuthorityService {
  AuthorityEntity findById(Long id);
  AllAuthoritiesResponse getAuthorities();
}
