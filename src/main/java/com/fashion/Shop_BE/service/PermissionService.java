package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestPermission;
import com.fashion.Shop_BE.dto.request.RequestUpdatePermission;
import com.fashion.Shop_BE.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    public PermissionResponse getById(String permissionName);
    public List<PermissionResponse> getAllPermissions();
    public PermissionResponse addPermission(RequestPermission permission);
    public PermissionResponse updatePermission(String permissionName, RequestUpdatePermission permission);
    public String deletePermission(String permission);



}
