package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestAddUser;
import com.fashion.Shop_BE.dto.request.RequestRegister;
import com.fashion.Shop_BE.dto.request.RequestUpdateUser;
import com.fashion.Shop_BE.dto.response.UserResponse;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target="roles",ignore = true)
    User requestRegisterToUser(RequestRegister requestRegister);

    UserResponse userToResponse(User user);

    @Mapping(target = "roles",ignore = true)
    @Mapping(target="userAvatar",ignore = true)
    void updateProfile(@MappingTarget User user, RequestUpdateUser requestUpdateUser);

    @Mapping(target = "roles",ignore = true)
    User requestAddUserToUser(RequestAddUser request);

    @Mapping(target = "roles",ignore = true)
    @Mapping(target="userAvatar",ignore = true)
    void updateUser(@MappingTarget User user, RequestAddUser requestAddUser);
}
