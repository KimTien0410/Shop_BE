package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.response.*;

import java.util.Date;
import java.util.List;

public interface StatisticService {
    public OrderStaticsResponse getOrderStatistics();
    public RevenueResponse getRevenueStatistics(Date startDate, Date endDate);
    public RevenueChartResponse getRevenueChart(String type,Date startDate, Date endDate);
    public StatisticResponse getTotalProductUserRevenue();
    public List<TopSellingProductResponse> getTopSellingProducts(Date startDate,Date endDate, int limit);
}
