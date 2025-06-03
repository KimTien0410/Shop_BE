package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.response.*;
import com.fashion.Shop_BE.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders/status")
    public ApiResponse<OrderStaticsResponse> getTotalOrders() {

        return ApiResponse.<OrderStaticsResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Thống kê đơn hàng theo trạng thái")
                .data(statisticService.getOrderStatistics())
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders/revenue")
    public ApiResponse<RevenueResponse> getTotalRevenueByDate(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return ApiResponse.<RevenueResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Thống kê doanh thu theo ngày")
                .data(statisticService.getRevenueStatistics(startDate, endDate))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/total-product-user-revenue")
    public ApiResponse<StatisticResponse> getTotalProductUserRevenue() {
        return ApiResponse.<StatisticResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Thống kê người dùng, sản phầm, doanh thu")
                .data(statisticService.getTotalProductUserRevenue())
                .build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/revenue-chart")
    public ApiResponse<RevenueChartResponse> getRevenueChart(
            @RequestParam String type,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return ApiResponse.<RevenueChartResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Thống kê doanh thu theo ngày")
                .data(statisticService.getRevenueChart(type,startDate, endDate))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-selling-products")
    public ApiResponse<List<TopSellingProductResponse>> getTopSellingProducts(
            @RequestParam Date startDate,
            @RequestParam Date endDate,
            @RequestParam(defaultValue = "5") int limit) {
        return ApiResponse.<List<TopSellingProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Thống kê sản phẩm bán chạy")
                .data(statisticService.getTopSellingProducts(startDate, endDate, limit))
                .build();
    }

}
