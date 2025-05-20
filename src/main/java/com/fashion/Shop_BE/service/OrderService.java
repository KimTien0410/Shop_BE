package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestOrder;
import com.fashion.Shop_BE.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    public ResponseEntity<?> placeOrder(RequestOrder request);
    public Page<OrderResponse> getAllOrderByUser(int page, int size, String sortBy, String sortDirection);
    public Page<OrderResponse> getOrderByStatus(String status,int page,int size, String sortBy, String sortDirection);
    public OrderResponse getOrderById(Long orderId);
    public void cancelOrder(Long orderId);
    public void updateOrderStatus(Long orderId);
    public Page<OrderResponse> getAllOrdersByAdmin(int page, int size, String sortBy, String sortDirection);
    public void adminCancelOrder(Long orderId);
}
