package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.response.admin.authority.AllAuthoritiesResponse;
import ru.sfu.zooshop.dto.response.admin.authority.AuthorityResponse;
import ru.sfu.zooshop.entity.AuthorityEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.AdminMapper;
import ru.sfu.zooshop.repository.AuthorityRepository;
import ru.sfu.zooshop.service.AuthorityService;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AuthorityServiceImpl implements AuthorityService {
  private final AuthorityRepository authorityRepository;
  private final AdminMapper adminMapper;

  @Override
  public AuthorityEntity findById(Long id) {
    return authorityRepository.findById(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Authority with ID " + id + " not found"));
  }

  @Override
  public AllAuthoritiesResponse getAuthorities() {
    Set<AuthorityResponse> authorities = authorityRepository.findAll().stream()
      .map(adminMapper::authorityEntityToAuthorityResponse)
      .collect(toSet());
    return new AllAuthoritiesResponse(authorities);
  }
}
