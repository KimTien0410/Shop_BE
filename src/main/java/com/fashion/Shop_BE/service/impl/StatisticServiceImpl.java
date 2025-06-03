package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.response.*;
import com.fashion.Shop_BE.enums.OrderStatus;
import com.fashion.Shop_BE.repository.OrderDetailRepository;
import com.fashion.Shop_BE.repository.OrderRepository;
import com.fashion.Shop_BE.repository.ProductRepository;
import com.fashion.Shop_BE.repository.UserRepository;
import com.fashion.Shop_BE.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;


    public OrderStaticsResponse getOrderStatistics(){
        OrderStaticsResponse orderStaticsResponse = new OrderStaticsResponse();
        orderStaticsResponse.setTotalOrders(orderRepository.count());
        orderStaticsResponse.setPendingOrders(orderRepository.countByOrderStatus(OrderStatus.PENDING));
        orderStaticsResponse.setInProgressOrders(orderRepository.countByOrderStatus(OrderStatus.IN_PROGRESS));
        orderStaticsResponse.setShippedOrders(orderRepository.countByOrderStatus(OrderStatus.SHIPPED));
        orderStaticsResponse.setDeliveredOrders(orderRepository.countByOrderStatus(OrderStatus.DELIVERED));
        orderStaticsResponse.setCanceledOrders(orderRepository.countByOrderStatus(OrderStatus.CANCELLED));
        return orderStaticsResponse;
    }

    @Override
    public RevenueResponse getRevenueStatistics(Date startDate, Date endDate) {
        Double totalRevenue = orderRepository.getTotalRevenue(startDate, endDate);

        return new RevenueResponse(totalRevenue,startDate, endDate);
    }

    @Override
    public RevenueChartResponse getRevenueChart(String type, Date startDate, Date endDate) {
        List<Object[]> results;
        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();
        if (type.equalsIgnoreCase("day")) {
            results = orderRepository.getDailyRevenue(startDate, endDate);
        } else if (type.equalsIgnoreCase("month")) {
            results = orderRepository.getMonthlyRevenue(startDate, endDate);
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        for (Object[] row : results) {
            labels.add(row[0].toString()); // Ngày hoặc tháng
            data.add((Double) row[1]); // Doanh thu
        }

        return new RevenueChartResponse(labels, data);
    }

    @Override
    public StatisticResponse getTotalProductUserRevenue() {
        return StatisticResponse.builder()
                .totalProducts(productRepository.countAllProducts())
                .totalUsers(userRepository.countAllUsers("USER"))
                .totalRevenue(orderRepository.getTotalRevenues())
                .build();
    }


    @Override
    public List<TopSellingProductResponse> getTopSellingProducts(Date startDate,Date endDate, int limit) {
            List<Object[]> rows = orderDetailRepository.getTopSellingProducts(startDate, endDate, limit);
//        return rows.stream().map(row ->
//                TopSellingProductResponse.builder()
//                        .productId(((BigDecimal) row[0]).longValue())
//                        .productName((String) row[1])
//                        .productImage((String) row[2])
//                        .totalSold(((BigDecimal) row[3]).longValue())
//                        .totalRevenue((Double) row[4])
//                        .build()
//        ).toList();
        return rows.stream().map(row -> {
            Long productId = ((Number) row[0]).longValue();
            String productName = (String) row[1];
            String productImage = (String) row[2];
            Long totalSold = ((Number) row[3]).longValue();

            BigDecimal totalRevenue;
            if (row[4] instanceof BigDecimal) {
                totalRevenue = (BigDecimal) row[4];
            } else {
                totalRevenue = BigDecimal.valueOf(((Number) row[4]).doubleValue());
            }

            return new TopSellingProductResponse(productId, productName, productImage, totalSold,totalRevenue);
        }).collect(Collectors.toList());
    }
}
