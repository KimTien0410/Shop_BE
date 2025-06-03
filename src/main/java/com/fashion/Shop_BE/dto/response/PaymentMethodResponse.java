package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodResponse {
    private Long paymentMethodId;
    private String paymentMethodName;
    private String paymentMethodLogo;
}
