package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.request.admin.role.RoleRequest;
import ru.sfu.zooshop.dto.response.admin.role.AllRolesResponse;
import ru.sfu.zooshop.dto.response.admin.role.BasicRoleResponse;
import ru.sfu.zooshop.dto.response.admin.role.RichRoleResponse;
import ru.sfu.zooshop.entity.AuthorityEntity;
import ru.sfu.zooshop.entity.RoleEntity;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.AdminMapper;
import ru.sfu.zooshop.repository.RoleRepository;
import ru.sfu.zooshop.repository.UserRepository;
import ru.sfu.zooshop.service.AuthorityService;
import ru.sfu.zooshop.service.RoleService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.*;
import static ru.sfu.zooshop.constant.Constant.*;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class RoleServiceImpl implements RoleService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final AdminMapper adminMapper;
  private final AuthorityService authorityService;

  private void validateId(Long id) {
    if (Objects.equals(id, SYSTEM_ROLE_ID)) throw new ApiException(FORBIDDEN, "Interactions with system role are not allowed");
    if (Objects.equals(id, ADMIN_ROLE_ID)) throw new ApiException(FORBIDDEN, "Interactions with admin role are not allowed");
    if (Objects.equals(id, USER_ROLE_ID)) throw new ApiException(FORBIDDEN, "Interactions with user role are not allowed");
  }

  private void validateName(String name) {
    if (roleRepository.existsByNameIgnoreCase(name)) throw new ApiException(CONFLICT, "Role with name " + name + " already exists");
  }

  @Override
  public RoleEntity findRoleById(Long id) {
    return roleRepository.findById(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Role with id " + id + " not found"));
  }

  @Override
  public RoleEntity findRoleByName(String name) {
    return roleRepository.findByNameIgnoreCase(name)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Role with name " + name + " not found"));
  }

  @Override
  public AllRolesResponse getRoles() {
    List<BasicRoleResponse> roles = roleRepository.findAll().stream()
      .map(adminMapper::roleEntityToBasicRoleResponse)
      .toList();
    return new AllRolesResponse(roles);
  }

  @Override
  public RichRoleResponse getRoleById(Long id) {
    RoleEntity role = findRoleById(id);
    return adminMapper.roleEntityToRichRoleResponse(role);
  }

  @Override
  public Long createRole(RoleRequest request) {
    validateName(request.getName());
    RoleEntity role = new RoleEntity();
    role.setName(request.getName());
    Set<AuthorityEntity> authorities = request.getAuthorities().stream()
      .map(authorityService::findById)
      .collect(toSet());
    role.setAuthorities(authorities);
    return roleRepository.save(role).getId();
  }

  @Override
  public void updateRole(Long id, RoleRequest request) {
    validateId(id);
    RoleEntity role = findRoleById(id);
    if (!Objects.equals(role.getName(), request.getName())) {
      validateName(request.getName());
    }
    role.setName(request.getName());
    Set<AuthorityEntity> authorities = request.getAuthorities().stream()
      .map(authorityService::findById)
      .collect(toSet());
    role.setAuthorities(authorities);
    roleRepository.save(role);
  }

  @Override
  public void deleteRole(Long id) {
    validateId(id);
    RoleEntity role = findRoleById(id);
    RoleEntity userRole = findRoleById(USER_ROLE_ID);
    List<UserEntity> users = userRepository.findAllByRole(role);
    users.forEach(user -> user.setRole(userRole));
    userRepository.saveAll(users);
    roleRepository.delete(role);
  }
}
