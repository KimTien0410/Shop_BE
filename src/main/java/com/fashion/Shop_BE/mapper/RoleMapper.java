package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestRole;
import com.fashion.Shop_BE.dto.request.RequestUpdateRole;
import com.fashion.Shop_BE.dto.response.RoleResponse;
import com.fashion.Shop_BE.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target="permissions",ignore = true)
    Role toRole(RequestRole requestRole);

    RoleResponse toRoleResponse(Role role);
    @Mapping(target="permissions",ignore = true)
//    @Mapping(target = "roleName", ignore = true)
    void requestUpdateToRole(@MappingTarget Role role, RequestUpdateRole requestUpdateRole);

}
