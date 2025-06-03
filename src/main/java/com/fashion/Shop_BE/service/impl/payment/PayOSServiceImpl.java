package com.fashion.Shop_BE.service.impl.payment;

import com.fashion.Shop_BE.entity.Order;
import com.fashion.Shop_BE.entity.Payment;
import com.fashion.Shop_BE.entity.PaymentMethod;
import com.fashion.Shop_BE.enums.OrderStatus;
import com.fashion.Shop_BE.enums.PaymentStatus;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.repository.OrderRepository;
import com.fashion.Shop_BE.repository.PaymentRepository;
import com.fashion.Shop_BE.service.PayOSService;
import com.fashion.Shop_BE.service.PaymentService;
import com.fashion.Shop_BE.service.impl.UpdateProductQuantity;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.exception.PayOSException;
import vn.payos.type.*;

@Service("payosService")
@RequiredArgsConstructor
public class PayOSServiceImpl implements PayOSService, PaymentService {
    private final PayOS payOS;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UpdateProductQuantity productQuantity;
    @Value("${CLIENT_URL}")
    private String clientUrl;

    @Override
    public String processPayment(Long orderId, Integer totalAmount) {
        try {
            // Tạo đối tượng PaymentData với thông tin thanh toán
            String returnUrl = clientUrl + "/order-history";
            String cancelUrl = clientUrl + "/order-history";
            PaymentData paymentData=PaymentData.builder()
                    .orderCode(orderId)
                    .description("Thanh toán đơn hàng "+orderId)
                    .amount(totalAmount)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .build();
            System.out.println("PaymentData: " + paymentData);
            CheckoutResponseData data= payOS.createPaymentLink(paymentData);
            return data.getCheckoutUrl();

        } catch (PayOSException e) {
            // Log the error for debugging
            System.err.println("PayOSException: " + e.getMessage());
            throw new RuntimeException("Error creating payment link: " + e.getMessage(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    @Override
    public String getPaymentUrl(Long orderId){
        try {
            String url = String.valueOf(payOS.getPaymentLinkInformation(orderId));
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void cancelPaymentLink(Long orderId) {
        try{
            payOS.cancelPaymentLink(orderId,"Huỷ đơn hàng!");
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
            Payment payment =paymentRepository.findByOrder(order)
                    .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
            order.setOrderStatus(OrderStatus.CANCELLED);
            payment.setPaymentStatus(PaymentStatus.FAILED);
            productQuantity.updateQuantityProductVariantAfterPlaceOrder(order);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void verifyWebhook(ObjectNode webhookBody) {
            //System.out.println("Webhook code: "+code);
        Long orderCode = webhookBody.get("data").get("orderCode").asLong();
        Order order = orderRepository.findById(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        PaymentMethod paymentMethod= order.getPaymentMethod();
        if(paymentMethod.getPaymentMethodName().equalsIgnoreCase("PayOS")){
            Payment payment =paymentRepository.findByOrder(order)
                    .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

            if(payment.getPaymentStatus().equals(PaymentStatus.PENDING)){
                payment.setPaymentStatus(PaymentStatus.COMPLETED);
                order.setOrderStatus(OrderStatus.IN_PROGRESS);
                orderRepository.save(order);
                paymentRepository.save(payment);
            }
        }
        else{
            throw new AppException(ErrorCode.PAYMENT_METHOD_NOT_PAY_OS);
        }

    }


}
