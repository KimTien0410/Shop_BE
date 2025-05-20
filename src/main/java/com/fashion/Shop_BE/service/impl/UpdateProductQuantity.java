package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.entity.Order;
import com.fashion.Shop_BE.entity.OrderDetail;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductVariant;
import com.fashion.Shop_BE.repository.ProductRepository;
import com.fashion.Shop_BE.repository.ProductVariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UpdateProductQuantity {
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    @Transactional
    public void updateQuantityProductVariantAfterPlaceOrder(Order order) {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            ProductVariant productVariant = orderDetail.getProductVariant();
            productVariant.setStockQuantity(productVariant.getStockQuantity() - orderDetail.getQuantity());

            Product product = productVariant.getProduct();
            product.setProductQuantity(product.getProductQuantity() - orderDetail.getQuantity());
            product.setProductSold(product.getProductSold() + orderDetail.getQuantity());
        }
        productVariantRepository.saveAll(orderDetails.stream().map(OrderDetail::getProductVariant).toList());
        productRepository.saveAll(orderDetails.stream().map(od -> od.getProductVariant().getProduct()).toList());
    }

    @Transactional
    public void updateQuantityProductVariantAfterCancelOrder(Order order) {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            ProductVariant productVariant = orderDetail.getProductVariant();
            productVariant.setStockQuantity(productVariant.getStockQuantity() + orderDetail.getQuantity());

            Product product = productVariant.getProduct();
            product.setProductQuantity(product.getProductQuantity() + orderDetail.getQuantity());
            product.setProductSold(product.getProductSold() - orderDetail.getQuantity());
        }
        productVariantRepository.saveAll(orderDetails.stream().map(OrderDetail::getProductVariant).toList());
        productRepository.saveAll(orderDetails.stream().map(od -> od.getProductVariant().getProduct()).toList());
    }
}
