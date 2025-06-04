package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestAddUser;
import com.fashion.Shop_BE.dto.request.RequestRegister;
import com.fashion.Shop_BE.dto.request.RequestUpdateUser;
import com.fashion.Shop_BE.dto.response.PermissionResponse;
import com.fashion.Shop_BE.dto.response.RoleResponse;
import com.fashion.Shop_BE.dto.response.UserResponse;
import com.fashion.Shop_BE.entity.Permission;
import com.fashion.Shop_BE.entity.Role;
import com.fashion.Shop_BE.entity.User;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-29T20:44:09+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User requestRegisterToUser(RequestRegister requestRegister) {
        if ( requestRegister == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( requestRegister.getEmail() );
        user.password( requestRegister.getPassword() );
        user.firstName( requestRegister.getFirstName() );
        user.lastName( requestRegister.getLastName() );

        return user.build();
    }

    @Override
    public UserResponse userToResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.userId( user.getUserId() );
        userResponse.email( user.getEmail() );
        userResponse.firstName( user.getFirstName() );
        userResponse.lastName( user.getLastName() );
        if ( user.getDateOfBirth() != null ) {
            userResponse.dateOfBirth( LocalDateTime.ofInstant( user.getDateOfBirth().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        userResponse.gender( user.isGender() );
        userResponse.userPhone( user.getUserPhone() );
        userResponse.userAvatar( user.getUserAvatar() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );
        userResponse.deletedAt( user.getDeletedAt() );
        userResponse.roles( roleSetToRoleResponseSet( user.getRoles() ) );

        return userResponse.build();
    }

    @Override
    public void updateProfile(User user, RequestUpdateUser requestUpdateUser) {
        if ( requestUpdateUser == null ) {
            return;
        }

        user.setFirstName( requestUpdateUser.getFirstName() );
        user.setLastName( requestUpdateUser.getLastName() );
        user.setUserPhone( requestUpdateUser.getUserPhone() );
        if ( requestUpdateUser.getDateOfBirth() != null ) {
            user.setDateOfBirth( Date.from( requestUpdateUser.getDateOfBirth().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        else {
            user.setDateOfBirth( null );
        }
        user.setGender( requestUpdateUser.isGender() );
    }

    @Override
    public User requestAddUserToUser(RequestAddUser request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( request.getEmail() );
        user.password( request.getPassword() );
        user.firstName( request.getFirstName() );
        user.lastName( request.getLastName() );

        return user.build();
    }

    @Override
    public void updateUser(User user, RequestAddUser requestAddUser) {
        if ( requestAddUser == null ) {
            return;
        }

        user.setEmail( requestAddUser.getEmail() );
        user.setPassword( requestAddUser.getPassword() );
        user.setFirstName( requestAddUser.getFirstName() );
        user.setLastName( requestAddUser.getLastName() );
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

    protected RoleResponse roleToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.roleName( role.getRoleName() );
        roleResponse.roleDescription( role.getRoleDescription() );
        roleResponse.permissions( permissionSetToPermissionResponseSet( role.getPermissions() ) );

        return roleResponse.build();
    }

    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleResponse( role ) );
        }

        return set1;
    }
}
