package com.fashion.Shop_BE.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingProductResponse {
    private Long productId;
    private String productName;
    private String productImage;
    private long totalSold;
    private BigDecimal totalRevenue;
}
