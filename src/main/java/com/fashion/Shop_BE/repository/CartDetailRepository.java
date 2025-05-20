package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.Cart;
import com.fashion.Shop_BE.entity.CartDetail;
import com.fashion.Shop_BE.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    Optional<CartDetail> findByCartAndProductVariant(Cart cart, ProductVariant productVariant);

    Optional<CartDetail> findByProductVariant(ProductVariant productVariant);
}
