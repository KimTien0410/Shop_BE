package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductResource;
import com.fashion.Shop_BE.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductResourceRepository extends JpaRepository<ProductResource, Long> {
    List<ProductResource> findByProduct(Product product);
    Optional<ProductResource> findFirstByProduct(Product product);

}
