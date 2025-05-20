package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStaticsResponse {
    private Long totalOrders;
    private Long pendingOrders;
    private Long inProgressOrders;
    private Long shippedOrders;
    private Long deliveredOrders;
    private Long canceledOrders;
}
