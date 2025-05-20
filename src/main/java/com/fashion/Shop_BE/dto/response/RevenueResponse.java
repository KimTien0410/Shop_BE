package com.fashion.Shop_BE.dto.response;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevenueResponse {
    private double totalRevenue;
    private Date startDate;
    private Date endDate;

}
