package com.fashion.Shop_BE.service.impl.payment;

import com.fashion.Shop_BE.service.PaymentService;
import org.springframework.stereotype.Service;

@Service("codService")
public class CODServiceImpl implements PaymentService {
    @Override
    public String processPayment(Long orderId, Integer totalAmount) {
        return null;
    }
}
