package com.fashion.Shop_BE.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrder {
    @NotNull(message="RECEIVER_ADDRESS_REQUIRED")
    private Long receiverAddressId;
    @NotNull(message="PAYMENT_METHOD_REQUIRED")
    private Long paymentMethodId;
    @NotNull(message="DELIVERY_METHOD_REQUIRED")
    private Long deliveryMethodId;
    private Long voucherId;
//
//    private String voucherCode;
    @NotNull(message="ORDER_DETAIL_REQUIRED")
    private List<RequestOrderDetail> orderDetails;
}
