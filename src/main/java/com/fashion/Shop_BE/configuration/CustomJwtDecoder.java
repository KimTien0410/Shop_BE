package com.fashion.Shop_BE.configuration;

import com.fashion.Shop_BE.dto.request.IntrospectRequest;
import com.fashion.Shop_BE.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;
@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.auth.secret_key}")
    protected String SECRET_KEY;
    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        var response = authenticationService.introrespect(
                IntrospectRequest.builder().token(token).build());
        if (!response.isValid()) {
            throw new BadJwtException("Token invalid!");
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HS256");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();
        }
        //System.out.println(nimbusJwtDecoder);
        return nimbusJwtDecoder.decode(token);
    }
}
