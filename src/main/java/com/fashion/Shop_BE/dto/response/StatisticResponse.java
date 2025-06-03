package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticResponse {
    private Long totalUsers;
    private Long totalProducts;
    private Double totalRevenue;
}
