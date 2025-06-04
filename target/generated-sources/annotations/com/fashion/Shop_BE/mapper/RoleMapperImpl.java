package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestRole;
import com.fashion.Shop_BE.dto.request.RequestUpdateRole;
import com.fashion.Shop_BE.dto.response.PermissionResponse;
import com.fashion.Shop_BE.dto.response.RoleResponse;
import com.fashion.Shop_BE.entity.Permission;
import com.fashion.Shop_BE.entity.Role;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T17:14:17+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toRole(RequestRole requestRole) {
        if ( requestRole == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.roleName( requestRole.getRoleName() );
        role.roleDescription( requestRole.getRoleDescription() );

        return role.build();
    }

    @Override
    public RoleResponse toRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.roleName( role.getRoleName() );
        roleResponse.roleDescription( role.getRoleDescription() );
        roleResponse.permissions( permissionSetToPermissionResponseSet( role.getPermissions() ) );

        return roleResponse.build();
    }

    @Override
    public void requestUpdateToRole(Role role, RequestUpdateRole requestUpdateRole) {
        if ( requestUpdateRole == null ) {
            return;
        }

        role.setRoleDescription( requestUpdateRole.getRoleDescription() );
    }

    protected PermissionResponse permissionToPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.permissionName( permission.getPermissionName() );
        permissionResponse.permissionDescription( permission.getPermissionDescription() );

        return permissionResponse.build();
    }

    protected Set<PermissionResponse> permissionSetToPermissionResponseSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionResponse> set1 = new LinkedHashSet<PermissionResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionToPermissionResponse( permission ) );
        }

        return set1;
    }
}
