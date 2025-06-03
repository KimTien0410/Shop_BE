package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.validation.ValidFile;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPaymentMethod {
    @NotBlank(message="PAYMENT_METHOD_NAME_NOT_BLANK")
    private String paymentMethodName;
    @ValidFile(message="FILE_INVALID")
    private MultipartFile paymentMethodLogo;

}
