package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestPaymentMethod;
import com.fashion.Shop_BE.dto.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    public List<PaymentMethodResponse> getAll();
    public PaymentMethodResponse getById(Long id);
    public PaymentMethodResponse save(RequestPaymentMethod requestPaymentMethod);
    public PaymentMethodResponse update(Long id,RequestPaymentMethod requestPaymentMethod);
    public List<PaymentMethodResponse> getAllByAdmin();
    public void changeStatusMethod(Long paymentMethodId);
}
