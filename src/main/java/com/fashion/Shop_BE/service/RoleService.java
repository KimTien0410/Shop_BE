package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestAddPermissionRoRole;
import com.fashion.Shop_BE.dto.request.RequestRole;
import com.fashion.Shop_BE.dto.request.RequestUpdateRole;
import com.fashion.Shop_BE.dto.response.RoleResponse;
import com.fashion.Shop_BE.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    public Set<Role> getUserRole(String roleName);
    public RoleResponse addRole(RequestRole role);
    public List<RoleResponse> getAllRole();
    public RoleResponse getRoleById(String id);
    public RoleResponse updateRole(String roleName, RequestUpdateRole role);
    public String deleteRole(String id);

    public RoleResponse addPermissionToRole(String roleName,RequestAddPermissionRoRole permissionNames);
    public RoleResponse deletePermissionFromRole(String roleName, RequestAddPermissionRoRole permissionNames);


}
