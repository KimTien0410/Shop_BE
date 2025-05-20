package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    @Query("SELECT p FROM PaymentMethod p WHERE p.paymentIsActive = true")
    List<PaymentMethod> findByPaymentIsActiveTrue();
}
