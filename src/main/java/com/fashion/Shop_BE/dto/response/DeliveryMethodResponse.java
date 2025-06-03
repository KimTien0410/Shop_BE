package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryMethodResponse {
    private Long deliveryMethodId;
    private String deliveryMethodName;
    private String deliveryFee;
    private String deliveryMethodLogo;
    private String deliveryMethodDescription;
}
