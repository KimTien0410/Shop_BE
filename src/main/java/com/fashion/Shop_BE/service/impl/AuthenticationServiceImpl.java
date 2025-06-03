package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.*;
import com.fashion.Shop_BE.dto.response.AuthenticationResponse;
import com.fashion.Shop_BE.dto.response.IntrorespectResponse;
import com.fashion.Shop_BE.entity.InvalidatedToken;
import com.fashion.Shop_BE.entity.Role;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.UserMapper;
import com.fashion.Shop_BE.repository.InvalidatedTokenRepository;
import com.fashion.Shop_BE.repository.UserRepository;
import com.fashion.Shop_BE.service.AuthenticationService;
import com.fashion.Shop_BE.service.EmailService;
import com.fashion.Shop_BE.service.RoleService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final RoleService roleService;

    @Value("${spring.application.name}")
    private String APP_Name;
    @Value("${jwt.auth.secret_key}")
    protected String SECRET_KEY;
    @Value("${jwt.auth.expires_in}")
    protected long EXPIRES_IN;
    @Value("${jwt.auth.duration_refresh}")
    protected long DURATION_REFRESH;


    @Transactional
    @Override
    public String register(RequestRegister request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.requestRegisterToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider("manual");

        String code = UUID.randomUUID().toString();
        user.setVerificationCode(code);

        Set<Role> roles=roleService.getUserRole("USER");
        user.setRoles(roles);

        userRepository.save(user);

        emailService.sendEmailToVerifyEmail(user);
        return "Đăng ký tài khoản thành công! Vui lòng kiểm tra email để xác thực tài khoản!";
    }

    @Override
    public String verifyEmail(String token) {
        User user=userRepository.findByVerificationCode(token)
                .orElseThrow(()-> new AppException(ErrorCode.TOKEN_INVALID));
        user.setEnabled(true); // Kích hoạt tài khoản
        user.setVerificationCode(null); // Xoá token sau khi xác thuc
        userRepository.save(user);
        return "Xác thực thành công!Bạn có thể đăng nhập!";
    }

    @Override
    public String resendVerificationCode(RequestResendVerification request) {
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!user.isEnabled()){
            String code = UUID.randomUUID().toString();
            user.setVerificationCode(code);
            return "Vui lòng vào mail để xác thực tài khoản!";
        }
        return "Bạn đã xác thực tài khoản!";
    }


    @Override
    public AuthenticationResponse authenticate(RequestAuthentication request) {
        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated) throw new AppException(ErrorCode.LOGIN_FAIL);
//        if(!user.isEnabled()){
//            throw new AppException(ErrorCode.ENABLE_FAIL);
//        };
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public IntrorespectResponse introrespect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e.getMessage());
        } catch (AppException e) {
            isValid = false;
        }
        return IntrorespectResponse.builder().valid(isValid).build();
    }

    @Override
    public void logout(String token) {
        try {
            var signToken = verifyToken(token, true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        } catch (AppException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
    }
    @Transactional
    @Override
    public AuthenticationResponse refreshToken(RequestRefreshToken request) {
        try {
            var signJwt = verifyToken(request.getToken(), true);
            // invalid token old
            var jit = signJwt.getJWTClaimsSet().getJWTID();
            var expiryTime = signJwt.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
            // issue new token
            var email = signJwt.getJWTClaimsSet().getSubject();
            var user = userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            var token = generateToken(user);
            return AuthenticationResponse.builder()
                    .token(token)
                    .build();
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }
    }


    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer(APP_Name)
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(EXPIRES_IN, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getPermissionName()));
                }
            });
        return stringJoiner.toString();
    }
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expityTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(DURATION_REFRESH, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if (!(verified && expityTime.after(new Date()))) throw new AppException(ErrorCode.LOGIN_FAIL);
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.LOGIN_FAIL);
        return signedJWT;
    }

}
