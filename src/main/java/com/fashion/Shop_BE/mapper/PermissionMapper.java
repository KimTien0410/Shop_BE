package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestPermission;
import com.fashion.Shop_BE.dto.request.RequestUpdatePermission;
import com.fashion.Shop_BE.dto.response.PermissionResponse;
import com.fashion.Shop_BE.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(RequestPermission requestPermission);
    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermission(@MappingTarget Permission permission, RequestUpdatePermission requestUpdatePermission);
}
