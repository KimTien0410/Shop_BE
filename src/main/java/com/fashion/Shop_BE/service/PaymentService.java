package com.fashion.Shop_BE.service;

public interface PaymentService {
    public String processPayment(Long orderId,Integer totalAmount);
}
