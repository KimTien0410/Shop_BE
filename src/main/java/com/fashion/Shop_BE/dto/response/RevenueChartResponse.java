package com.fashion.Shop_BE.dto.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevenueChartResponse {
    private List<String> labels;
    private List<Double> data;

}
