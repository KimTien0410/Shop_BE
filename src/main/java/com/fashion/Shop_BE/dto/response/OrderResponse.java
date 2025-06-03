package com.fashion.Shop_BE.dto.response;

import com.fashion.Shop_BE.enums.PaymentStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String voucherCode;
    private String orderStatus;
    private Date orderDate;
    private String paymentMethodName;
    private String deliveryMethodName;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private double totalPrice;
    private double discountPrice;
    private double shippingFee;
    private double finalPrice;
    private PaymentStatus paymentStatus;
    private List<OrderDetailResponse> orderDetails;
}
