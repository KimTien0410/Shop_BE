package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.validation.ValidFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import java.security.PrivateKey;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDeliveryMethod {
    @NotBlank(message="DELIVERY_METHOD_NAME_NOT_BLANK")
    private String deliveryMethodName;
    @NotNull(message="DELIVERY_METHOD_FEE_NOT_BLANK")
    private Double deliveryFee;
    @ValidFile(message="FILE_INVALID")
    private MultipartFile deliveryMethodLogo;
    private String deliveryMethodDescription;

}
