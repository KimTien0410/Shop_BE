package com.fashion.Shop_BE.service.impl.payment;

import com.fashion.Shop_BE.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl  {
    private final PaymentRepository paymentRepository;

}
