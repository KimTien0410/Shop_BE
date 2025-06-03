package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestAddPermissionRoRole;
import com.fashion.Shop_BE.dto.request.RequestRole;
import com.fashion.Shop_BE.dto.request.RequestUpdateRole;
import com.fashion.Shop_BE.dto.response.RoleResponse;
import com.fashion.Shop_BE.entity.Permission;
import com.fashion.Shop_BE.entity.Role;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.RoleMapper;
import com.fashion.Shop_BE.repository.PermissionRepository;
import com.fashion.Shop_BE.repository.RoleRepository;
import com.fashion.Shop_BE.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper mapper;
    private final PermissionRepository permissionRepository;

    @Override
    public List<RoleResponse> getAllRole() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponse> roleResponses = new ArrayList<>();
        for (Role role : roles) {
            roleResponses.add(roleMapper.toRoleResponse(role));
        }
        return roleResponses;
    }

    @Override
    public RoleResponse getRoleById(String id) {
        Role role=roleRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
        return roleMapper.toRoleResponse(role);
    }
    @Transactional
    @Override
    public RoleResponse updateRole(String roleName, RequestUpdateRole request) {
        Role role=roleRepository.findById(roleName).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
        roleMapper.requestUpdateToRole(role, request);
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
    @Transactional
    @Override
    public String deleteRole(String id) {
        Role role=roleRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));

        roleRepository.deleteById(id);
        return "Xoá role thành công";
    }

    @Override
    public RoleResponse addPermissionToRole(String roleName, RequestAddPermissionRoRole request) {
        Role role=roleRepository.findById(roleName)
                .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
        var permissions =request.getPermissions();
        if(permissions!=null){
            List<Permission> permissionList = permissionRepository.findAllById(permissions);
            role.getPermissions().addAll(permissionList);
        }
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse deletePermissionFromRole(String roleName,RequestAddPermissionRoRole request) {
        Role role=roleRepository.findById(roleName)
                .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
        var permissions =request.getPermissions();
        if(permissions!=null){
            List<Permission> permissionList = permissionRepository.findAllById(permissions);
            for(Permission permission:permissionList){
                if(role.getPermissions().contains(permission)){
                    role.getPermissions().remove(permission);
                }
            }
        }
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    private final RoleMapper roleMapper;

    @Override
    public Set<Role> getUserRole(String roleName) {
        Set<Role> roles = new HashSet<>();
        Role role=roleRepository.findByRoleName(roleName);
        roles.add(role);
        return roles;
    }
    @Transactional
    @Override
    public RoleResponse addRole(RequestRole requestRole) {
        Role role=roleMapper.toRole(requestRole);
        var permissions = permissionRepository.findAllById(requestRole.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
}
