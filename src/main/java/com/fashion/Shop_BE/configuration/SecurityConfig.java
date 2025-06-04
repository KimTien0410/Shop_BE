package com.fashion.Shop_BE.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] PUBLIC_POST_URL = {
            "/auth/register","/auth/login","/auth/introspect","/auth/resend-verification","/payments/confirm-webhook","products/search"
    };
    private final String[] PUBLIC_GET_URL = {
            "/auth/verify","/brands","/brands/**","/products","/products/**","/categories","/categories/**",
            "variants/**","variants","payment-methods","payment-methods/**","delivery-methods","delivery-methods/**",
            "vouchers","vouchers/**","reviews","reviews/**"
    };


//    @Autowired
    private CustomJwtDecoder jwtDecoder;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.POST, PUBLIC_POST_URL).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(HttpMethod.GET,PUBLIC_GET_URL).permitAll()
                .anyRequest()
                .authenticated());

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(jwtDecoder).jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
//        http.cors(c->corsFilter());
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())); // ✅ đúng
        http.csrf(AbstractHttpConfigurer::disable); // csrf : protect some endpoint not attack
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:3000","https://shopfashionkt.netlify.app")); // Cho phép FE
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("*")); // Có thể cụ thể hơn nếu cần
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("X-Total-Count", "content-range", "Content-Type", "Accept", "X-Requested-With", "remember-me"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000")); // Allow frontend origin
////    config.setAllowedOriginPatterns(Collections.singletonList("*"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
//        config.setAllowCredentials(true); // nếu frontend có dùng credentials (cookies, Authorization headers,...)
//        config.setExposedHeaders(Arrays.asList("X-Total-Count", "content-range", "Content-Type", "Accept", "X-Requested-With", "remember-me"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return converter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(10);
    }


}
