package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestPermission;
import com.fashion.Shop_BE.dto.request.RequestUpdatePermission;
import com.fashion.Shop_BE.dto.response.PermissionResponse;
import com.fashion.Shop_BE.entity.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-29T20:44:08+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermission(RequestPermission requestPermission) {
        if ( requestPermission == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.permissionName( requestPermission.getPermissionName() );
        permission.permissionDescription( requestPermission.getPermissionDescription() );

        return permission.build();
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.permissionName( permission.getPermissionName() );
        permissionResponse.permissionDescription( permission.getPermissionDescription() );

        return permissionResponse.build();
    }

    @Override
    public void updatePermission(Permission permission, RequestUpdatePermission requestUpdatePermission) {
        if ( requestUpdatePermission == null ) {
            return;
        }

        permission.setPermissionDescription( requestUpdatePermission.getPermissionDescription() );
    }
}
