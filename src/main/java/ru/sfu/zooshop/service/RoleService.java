package ru.sfu.zooshop.service;


import ru.sfu.zooshop.dto.request.admin.role.RoleRequest;
import ru.sfu.zooshop.dto.response.admin.role.AllRolesResponse;
import ru.sfu.zooshop.dto.response.admin.role.RichRoleResponse;
import ru.sfu.zooshop.entity.RoleEntity;

public interface RoleService {
  RoleEntity findRoleById(Long id);
  RoleEntity findRoleByName(String name);
  AllRolesResponse getRoles();
  RichRoleResponse getRoleById(Long id);
  Long createRole(RoleRequest request);
  void updateRole(Long id, RoleRequest request);
  void deleteRole(Long id);
}
