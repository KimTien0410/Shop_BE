package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestOrder;
import com.fashion.Shop_BE.dto.request.RequestOrderDetail;
import com.fashion.Shop_BE.dto.response.OrderDetailResponse;
import com.fashion.Shop_BE.dto.response.OrderResponse;
import com.fashion.Shop_BE.entity.*;
import com.fashion.Shop_BE.enums.DiscountType;
import com.fashion.Shop_BE.enums.OrderStatus;
import com.fashion.Shop_BE.enums.PaymentStatus;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.repository.*;
import com.fashion.Shop_BE.service.*;
import com.fashion.Shop_BE.service.impl.payment.PaymentFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ReceiverAddressRepository receiverAddressRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final ProductRepository productRepository;
    private final VoucherRepository voucherRepository;
    private final ProductVariantRepository productVariantRepository;
    private final VoucherService voucherService;
    private final ProductService productService;
    private final UserService userService;
    private final PaymentFactory paymentFactory;
    private final UpdateProductQuantity productQuantity;
    private final EmailService emailService;
    public OrderResponse toOrderResponse(Order order) {
        ReceiverAddress receiverAddress= order.getReceiverAddress();
        String fullAddress = String.format("%s, %s, %s, %s",
                receiverAddress.getStreet(),
                receiverAddress.getWard(),
                receiverAddress.getDistrict(),
                receiverAddress.getCity());

        List<OrderDetailResponse> orderDetailResponses= toOrderDetailResponses(order.getOrderDetails());
        String voucherCode= order.getVoucher() != null ? order.getVoucher().getVoucherCode() : null;
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .receiverName(receiverAddress.getReceiverName())
                .receiverPhone(receiverAddress.getReceiverPhone())
                .receiverAddress(fullAddress)
                .paymentMethodName(order.getPaymentMethod().getPaymentMethodName())
                .orderStatus(order.getOrderStatus().toString())
                .orderDate(order.getOrderDate())
                .deliveryMethodName(order.getDeliveryMethod().getDeliveryMethodName())
                .shippingFee(order.getDeliveryMethod().getDeliveryFee())
                .voucherCode(voucherCode)
                .totalPrice(order.getOrderTotalAmount())
                .finalPrice(order.getOrderTotalAmount() - (order.getVoucher() != null ? order.getVoucher().getDiscountValue() : 0))
                .orderDetails(orderDetailResponses)
                .build();
    }
    public List<OrderDetailResponse> toOrderDetailResponses(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(this::toOrderDetailResponse)
                .toList();
    }
    public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        Product product = orderDetail.getProductVariant().getProduct();

        return OrderDetailResponse.builder()
                .orderDetailId(orderDetail.getOrderDetailId())
                .productId(product.getProductId())
                .productVariantId(orderDetail.getProductVariant().getVariantId())
                .productName(product.getProductName())
                .productImage(productService.getThumbnailImage(product))
                .size(orderDetail.getProductVariant().getSize())
                .color(orderDetail.getProductVariant().getColor())
                .price( orderDetail.getItemPrice())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .build();
    }

    @Transactional
    @Override
    public ResponseEntity<?> placeOrder(RequestOrder request) {

        User user = userService.getUserAuthenticated();
        ReceiverAddress receiverAddress= receiverAddressRepository.findById(request.getReceiverAddressId())
                .orElseThrow(()->new AppException(ErrorCode.RECEIVER_ADDRESS_NOT_FOUND));
        PaymentMethod paymentMethod=paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(()->new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));
        DeliveryMethod deliveryMethod=deliveryMethodRepository.findById(request.getDeliveryMethodId())
                .orElseThrow(()->new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));

        Voucher voucher = null;
        if (request.getVoucherId() != null) {
            voucher = voucherRepository.findById(request.getVoucherId())
                    .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND));
        }

        Order order=new Order();
        order.setUser(user);
        order.setReceiverAddress(receiverAddress);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(new Date());
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryMethod(deliveryMethod);
        order.setVoucher(voucher);

        double totalPrice=deliveryMethod.getDeliveryFee();
        order.setOrderTotalAmount(totalPrice);

        orderRepository.save(order);

        // Create order details
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (RequestOrderDetail orderDetail : request.getOrderDetails()) {
            ProductVariant productVariant = productVariantRepository.findById(orderDetail.getProductVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
            if (productVariant.getStockQuantity() < orderDetail.getQuantity()) {
                throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_ENOUGH_QUANTITY);
            }
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProductVariant(productVariant);
            detail.setQuantity(orderDetail.getQuantity());
            detail.setItemPrice(productVariant.getVariantPrice());
            detail.setTotalPrice(productVariant.getVariantPrice() * orderDetail.getQuantity());

            totalPrice += detail.getTotalPrice();

            productVariant.setStockQuantity(productVariant.getStockQuantity() - orderDetail.getQuantity());
            productVariantRepository.save(productVariant);

            // Cập nhật số lượng sản phẩm trong kho
            Product product= productVariant.getProduct();
            product.setProductQuantity(productVariant.getProduct().getProductQuantity() -
                    orderDetail.getQuantity());
            product.setProductSold(product.getProductSold() + orderDetail.getQuantity());
            productRepository.save(product);
            orderDetails.add(detail);
        }
        // Save order details
        orderDetailRepository.saveAll(orderDetails);

        if (voucher != null) {
            double discountedPrice =0;
            if (voucher.getDiscountType().equals(DiscountType.PERCENTAGE)) {
                 discountedPrice=totalPrice * (voucher.getDiscountValue() / 100);
                totalPrice = totalPrice * (1 - voucher.getDiscountValue() / 100);
            } else if (voucher.getDiscountType().equals(DiscountType.FIXED)) {
                discountedPrice = voucher.getDiscountValue();
                totalPrice = Math.max(0, totalPrice - voucher.getDiscountValue());
            }
            order.setOrderDiscount(discountedPrice);
            voucher.setQuantity(voucher.getQuantity() - 1);
            voucherRepository.save(voucher);
        }

        order.setOrderTotalAmount(totalPrice);
        order.setOrderDetails(orderDetails);

        orderRepository.save(order);
        //Create payment
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentAt(new Date());
        paymentRepository.save(payment);

        String subject = "Order Confirmation";
        String body = String.format("Dear %s,\n\nYour order #%d has been placed successfully.\n\nThank you for shopping with us!",
                user.getEmail(), order.getOrderId());
        emailService.sendEmail(user.getEmail(), subject, body);
        // Process payment
        PaymentService paymentService = paymentFactory.getPaymentService(paymentMethod.getPaymentMethodId().intValue());
        String handlePayment=paymentService.processPayment(order.getOrderId(),order.getOrderTotalAmount().intValue());

        if(handlePayment!=null){
            return ResponseEntity.ok().body(handlePayment);
        }

        return ResponseEntity.ok().body(toOrderResponse(order));
    }

    @Override
    public Page<OrderResponse> getAllOrderByUser(int page, int size, String sortBy, String sortDirection) {
        User user = userService.getUserAuthenticated();
        Sort sort = Sort.by(sortBy);
        if (sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())) {
            sort = sort.ascending();
        }
        else{
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findByUser(user, pageable);

        return orders.map(this::toOrderResponse);
    }

    @Override
    public Page<OrderResponse> getOrderByStatus(String status,int page, int size, String sortBy, String sortDirection) {
        User user = userService.getUserAuthenticated();
        Sort sort = Sort.by(sortBy);
        if (sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())) {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findByUserAndOrderStatus(user, OrderStatus.valueOf(status), pageable);
        return orders.map(this::toOrderResponse);
    }


    @Override
    public OrderResponse getOrderById(Long orderId) {
        User user = userService.getUserAuthenticated();
        Order order = orderRepository.findByOrderIdAndUser(orderId, user)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return toOrderResponse(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        User user = userService.getUserAuthenticated();
        Order order = orderRepository.findByOrderIdAndUser(orderId,user)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new AppException(ErrorCode.ORDER_ALREADY_CANCELLED);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        productQuantity.updateQuantityProductVariantAfterCancelOrder(order);
        orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if ("PayOS".equalsIgnoreCase(order.getPaymentMethod().getPaymentMethodName())) {
            Payment payment = paymentRepository.findByOrder(order)
                    .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
            if (payment.getPaymentStatus() == PaymentStatus.COMPLETED) {
                updateOrderStatus(order);
            } else {
                order.setOrderStatus(OrderStatus.CANCELLED);
                productQuantity.updateQuantityProductVariantAfterCancelOrder(order);
            }
        } else {
            updateOrderStatus(order);
        }
        User user =order.getUser();
        String subject = "Order Confirmation";
        String body = String.format("Dear %s,\n\nYour order #%d has been placed successfully.\n\nThank you for shopping with us!",
                user.getEmail(), order.getOrderId());
        emailService.sendEmail(user.getEmail(), subject, body);
        orderRepository.save(order);

    }
    private void updateOrderStatus(Order order) {
        switch (order.getOrderStatus()) {
            case PENDING:
                order.setOrderStatus(OrderStatus.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                order.setOrderStatus(OrderStatus.SHIPPED);
                break;
            case SHIPPED:
                order.setOrderStatus(OrderStatus.DELIVERED);
                order.getPayment().setPaymentStatus(PaymentStatus.COMPLETED);
                paymentRepository.save(order.getPayment());
                break;
            case DELIVERED:
                throw new AppException(ErrorCode.ORDER_ALREADY_DELIVERED);
            default:
                throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }


    @Override
    public Page<OrderResponse> getAllOrdersByAdmin(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(this::toOrderResponse);
    }

    @Override
    public void adminCancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new AppException(ErrorCode.ORDER_ALREADY_CANCELLED);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        productQuantity.updateQuantityProductVariantAfterCancelOrder(order);
    }
}
