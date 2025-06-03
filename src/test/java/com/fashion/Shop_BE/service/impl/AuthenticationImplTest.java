package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestAuthentication;
import com.fashion.Shop_BE.dto.request.RequestRegister;
import com.fashion.Shop_BE.dto.response.AuthenticationResponse;
import com.fashion.Shop_BE.entity.Role;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.UserMapper;
import com.fashion.Shop_BE.repository.UserRepository;
import com.fashion.Shop_BE.service.AuthenticationService;
import com.fashion.Shop_BE.service.EmailService;
import com.fashion.Shop_BE.service.RoleService;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AuthenticationImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private RoleService roleService;

    @Mock
    private EmailService emailService;
    @InjectMocks
    private AuthenticationServiceImpl authenticationImpl;
    private RequestRegister requestRegister;
    @BeforeEach
    void setUp() {
        requestRegister =  RequestRegister.builder()
                .email("test@test.com")
                .password("password")
                .build();
//        requestRegister.setEmail("test@example.com");
//        requestRegister.setPassword("password");
        // Mock the SECRET_KEY field
        ReflectionTestUtils.setField(authenticationImpl, "SECRET_KEY", "your256bitSecretKeyHereMockSecretKey32");

        // Spy the AuthenticationServiceImpl to mock specific methods
        authenticationImpl = spy(authenticationImpl);
    }
    @Test
    void register_ShouldRegisterUser_WhenEmailDoesNotExist() {
        // Arrange
        User user = new User();
        user.setEmail(requestRegister.getEmail());
        user.setPassword(requestRegister.getPassword());

        when(userRepository.findByEmail(requestRegister.getEmail())).thenReturn(Optional.empty());
        when(userMapper.requestRegisterToUser(requestRegister)).thenReturn(user);
        when(passwordEncoder.encode(requestRegister.getPassword())).thenReturn("encodedPassword");
        when(roleService.getUserRole("USER")).thenReturn(Set.of(new Role()));
        doNothing().when(emailService).sendEmailToVerifyEmail(user);

        // Act
        String result = authenticationImpl.register(requestRegister);

        // Assert
        assertEquals("Đăng ký tài khoản thành công! Vui lòng kiểm tra email để xác thực tài khoản!", result);
        verify(userRepository, times(1)).findByEmail(requestRegister.getEmail());
        verify(userMapper, times(1)).requestRegisterToUser(requestRegister);
        verify(passwordEncoder, times(1)).encode(requestRegister.getPassword());
        verify(roleService, times(1)).getUserRole("USER");
        verify(userRepository, times(1)).save(user);
        verify(emailService, times(1)).sendEmailToVerifyEmail(user);
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail(requestRegister.getEmail())).thenReturn(Optional.of(new User()));

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> authenticationImpl.register(requestRegister));
        assertEquals(ErrorCode.USER_EXISTED, exception.getErrorCode());
        verify(userRepository, times(1)).findByEmail(requestRegister.getEmail());
        verifyNoInteractions(userMapper, passwordEncoder, roleService, emailService);
    }
        @Test
        void authenticate_ShouldReturnToken_WhenCredentialsAreValid() {
            // Arrange
            RequestAuthentication request = new RequestAuthentication();
            request.setEmail("test@example.com");
            request.setPassword("password");

            User user = new User();
            user.setEmail("test@example.com");
            user.setPassword("encodedPassword");

            when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
            doReturn("mockToken").when(authenticationImpl).generateToken(user);

            // Act
            AuthenticationResponse response = authenticationImpl.authenticate(request);

            // Assert
            assertNotNull(response);
            assertEquals("mockToken", response.getToken());
            verify(userRepository, times(1)).findByEmail(request.getEmail());
            verify(passwordEncoder, times(1)).matches(request.getPassword(), user.getPassword());
        }

    @Test
    void authenticate_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        RequestAuthentication request = new RequestAuthentication();
        request.setEmail("nonexistent@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> authenticationImpl.authenticate(request));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void authenticate_ShouldThrowException_WhenPasswordDoesNotMatch() {
        // Arrange
        RequestAuthentication request = new RequestAuthentication();
        request.setEmail("test@example.com");
        request.setPassword("wrongPassword");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> authenticationImpl.authenticate(request));
        assertEquals(ErrorCode.LOGIN_FAIL, exception.getErrorCode());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).matches(request.getPassword(), user.getPassword());
    }

}
