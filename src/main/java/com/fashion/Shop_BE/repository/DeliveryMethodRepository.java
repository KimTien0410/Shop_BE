package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {
    @Query("SELECT d FROM DeliveryMethod d WHERE d.deliveryIsActive = true")
    List<DeliveryMethod> findByDeliveryIsActiveTrue();
}
