package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestAddUser;
import com.fashion.Shop_BE.dto.request.RequestRegister;
import com.fashion.Shop_BE.dto.request.RequestUpdateUser;
import com.fashion.Shop_BE.dto.response.UserResponse;
import com.fashion.Shop_BE.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    public User getUserAuthenticated();
    public Page<UserResponse> getUsersByRole(String roleName, int page, int size);
    public Page<UserResponse> getAllUsers(String search, int page, int size, String sortBy, String sortDirection);
    public UserResponse getProfile();
    public UserResponse getUserById(Long id);
    public UserResponse updateProfile(RequestUpdateUser request);
    public void deleteUserById(Long id);
    public void restoreUserById(Long id);
    public UserResponse addRoleToUser(Long userId, String roleName);
    public UserResponse removeRoleFromUser(Long userId, String roleName);

    public UserResponse updateUser(Long userId, RequestAddUser requestUpdateUser);
    public UserResponse addUser(RequestAddUser requestRegister);
}
