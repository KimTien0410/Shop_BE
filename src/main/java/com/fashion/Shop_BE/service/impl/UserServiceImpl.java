package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestAddUser;
import com.fashion.Shop_BE.dto.request.RequestRegister;
import com.fashion.Shop_BE.dto.request.RequestUpdateUser;
import com.fashion.Shop_BE.dto.response.UserResponse;
import com.fashion.Shop_BE.entity.Role;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.UserMapper;
import com.fashion.Shop_BE.repository.RoleRepository;
import com.fashion.Shop_BE.repository.UserRepository;
import com.fashion.Shop_BE.service.RoleService;
import com.fashion.Shop_BE.service.UserService;
import com.fashion.Shop_BE.service.CloudinaryService;
import com.fashion.Shop_BE.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;
    private final RoleService roleService;
    @Override
    public User getUserAuthenticated() {
        var context= SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Page<UserResponse> getUsersByRole(String roleName,int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users=userRepository.findUsersByRole(roleName,pageable);
        return users.map(userMapper::userToResponse);
    }

    @Override
    public Page<UserResponse> getAllUsers(String search, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users;
        if (search == null || search.trim().isEmpty()) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.searchUsers(search, pageable);
        }

        return users.map(userMapper::userToResponse);
    }

    @Override
    public UserResponse getProfile() {
//        var context= SecurityContextHolder.getContext();
//        String email=context.getAuthentication().getName();
        String email = getAuthenticatedUsername();
        User user=userRepository.findByEmail(email).orElseThrow(
                ()->new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.userToResponse(user);
    }

    @Override
    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse getUserById(Long id) {
        return userMapper.userToResponse(userRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    @Transactional
    public UserResponse updateProfile(RequestUpdateUser request) {
        String email = getAuthenticatedUsername();
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateProfile(user, request);

        MultipartFile avatarFile=request.getAvatar();
        if(avatarFile!=null && !avatarFile.isEmpty()){
            if(user.getUserAvatar()!=null && !user.getUserAvatar().isEmpty()){
                cloudinaryService.deleteOldFile(user.getUserAvatar());
            }
            String url=cloudinaryService.uploadFile(avatarFile);
            user.setUserAvatar(url);
        }

        userRepository.save(user);

        return userMapper.userToResponse(user);
    }
    @Transactional
    @Override
    public void deleteUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        user.setDeletedAt(new Date());
        userRepository.save(user);
    }
    @Transactional
    @Override
    public void restoreUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        user.setDeletedAt(null);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserResponse addRoleToUser(Long userId, String roleName) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        Role role=roleRepository.findById(roleName)
                .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.getRoles().add(role);
        userRepository.save(user);
        return userMapper.userToResponse(user);
    }
    @Transactional
    @Override
    public UserResponse removeRoleFromUser(Long userId, String roleName) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        Role role=roleRepository.findById(roleName)
                .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.getRoles().remove(role);
        userRepository.save(user);
        return userMapper.userToResponse(user);
    }

    private String getAuthenticatedUsername(){
        var context= SecurityContextHolder.getContext();
         return context.getAuthentication().getName();
    }

    @Override
    public UserResponse updateUser(Long userId, RequestAddUser requestUpdateUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, requestUpdateUser);
        Set<Role> roles=roleService.getUserRole(requestUpdateUser.getRole());
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.userToResponse(user);
    }

    @Override
    public UserResponse addUser(RequestAddUser request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.requestAddUserToUser(request);
//        System.out.println("request: "+ request.getRole());
//        System.out.println("user"+ user );
//        System.out.println("password: "+request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider("manual");

        String code = UUID.randomUUID().toString();
        user.setVerificationCode(code);

        Set<Role> roles=roleService.getUserRole(request.getRole());
        user.setRoles(roles);

        userRepository.save(user);

        return null;
    }
}
