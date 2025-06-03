package com.fashion.Shop_BE.service.impl.payment;


import com.fashion.Shop_BE.service.PaymentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PaymentFactory {
    private final PaymentService codService;
    private final PaymentService payosService;
    public PaymentFactory(
            @Qualifier("codService") PaymentService codService,
            @Qualifier("payosService") PaymentService payosService) {
        this.codService = codService;
        this.payosService = payosService;
    }
    public PaymentService getPaymentService(int paymentMethodId){
        switch (paymentMethodId){
            case 1:
                return codService;  // COD payment service
            case 2:
                return payosService; // PayOS payment service
            default:
                throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ!");
        }
    }
}
