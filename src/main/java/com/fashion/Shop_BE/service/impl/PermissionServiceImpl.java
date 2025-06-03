package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestPermission;
import com.fashion.Shop_BE.dto.request.RequestUpdatePermission;
import com.fashion.Shop_BE.dto.response.PermissionResponse;
import com.fashion.Shop_BE.entity.Permission;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.PermissionMapper;
import com.fashion.Shop_BE.repository.PermissionRepository;
import com.fashion.Shop_BE.service.PermissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    @Override
    public PermissionResponse getById(String permissionName) {
        Permission permission=permissionRepository.findById(permissionName)
                .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions=permissionRepository.findAll();
        List<PermissionResponse> permissionResponses=new ArrayList<>();
        permissions.stream().forEach(permission->{
            permissionResponses.add(permissionMapper.toPermissionResponse(permission));
        });
        return permissionResponses;
    }
    @Transactional
    @Override
    public PermissionResponse addPermission(RequestPermission permission) {
        Permission permission1=permissionMapper.toPermission(permission);
        permissionRepository.save(permission1);
        return permissionMapper.toPermissionResponse(permission1);
    }
    @Transactional
    @Override
    public PermissionResponse updatePermission(String permissionName, RequestUpdatePermission description) {
        Permission permission=permissionRepository.findById(permissionName)
                .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionMapper.updatePermission(permission,description);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    @Transactional
    @Override
    public String deletePermission(String permissionName) {
        Permission permission=permissionRepository.findById(permissionName)
                .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionRepository.delete(permission);
        return "Xoá permission thành công!";
    }
}
