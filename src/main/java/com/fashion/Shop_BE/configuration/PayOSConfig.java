package com.fashion.Shop_BE.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {
    @Value("${PAYOS_CLIENT_ID}")
    private String payOSClientId;
    @Value("${PAYOS_API_KEY}")
    private String payOSApiKey;
    @Value("${PAYOS_CHECKSUM_KEY}")
    private String payOSChecksumKey;

    @Bean
    public PayOS payOS(){
        return new PayOS(payOSClientId, payOSApiKey, payOSChecksumKey);
    }

}
