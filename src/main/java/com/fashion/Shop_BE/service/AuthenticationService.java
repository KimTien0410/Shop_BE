package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.*;
import com.fashion.Shop_BE.dto.response.AuthenticationResponse;
import com.fashion.Shop_BE.dto.response.IntrorespectResponse;


public interface AuthenticationService {
    public String register(RequestRegister request);
    public String verifyEmail(String token);
    public String resendVerificationCode(RequestResendVerification request);
    public AuthenticationResponse authenticate(RequestAuthentication request);
    public IntrorespectResponse introrespect(IntrospectRequest request);
    public void logout(String token);
    public AuthenticationResponse refreshToken(RequestRefreshToken request);
}
