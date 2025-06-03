package com.fashion.Shop_BE.specification;

import com.fashion.Shop_BE.dto.request.ProductFilterRequest;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductResource;
import com.fashion.Shop_BE.entity.ProductVariant;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ProductSpecification {
    public static Specification<Product> filterBy(ProductFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search theo tên
            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("productName")), "%" + filter.getSearch().toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("productDescription")), "%" + filter.getSearch().toLowerCase() + "%"));
            }


            // Filter category
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("categoryId"), filter.getCategoryId()));
            }

            // Filter brand
            if (filter.getBrandCode() != null && !filter.getBrandCode().isEmpty()) {
                predicates.add(root.get("brand").get("brandCode").in(filter.getBrandCode()));
            }

            // Filter khoảng giá
            if (filter.getMinPrice() != null || filter.getMaxPrice() != null) {
                if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
                    predicates.add(cb.between(root.get("basePrice"), filter.getMinPrice(), filter.getMaxPrice()));
                } else if (filter.getMinPrice() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("basePrice"), filter.getMinPrice()));
                } else {
                    predicates.add(cb.lessThanOrEqualTo(root.get("basePrice"), filter.getMaxPrice()));
                }
            }

            // Filter sản phẩm mới
            if (Boolean.TRUE.equals(filter.getIsNewArrival())) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), LocalDateTime.now().minusDays(30)));
            }

            // Filter sản phẩm bán chạy
            if (Boolean.TRUE.equals(filter.getIsBestSeller())) {
                predicates.add(cb.greaterThan(root.get("productSold"), 5));
            }

            // Join đến variant để filter theo màu, size
            if ((filter.getColor() != null && !filter.getColor().isEmpty()) ||
                    (filter.getSize() != null && !filter.getSize().isEmpty())) {

                Join<Product, ProductVariant> variantJoin = root.join("productVariants", JoinType.INNER);

                if (filter.getColor() != null && !filter.getColor().isEmpty()) {
                    predicates.add(variantJoin.get("color").in(filter.getColor()));
                }
                if (filter.getSize() != null && !filter.getSize().isEmpty()) {
                    predicates.add(variantJoin.get("size").in(filter.getSize()));
                }
                predicates.add(root.get("deletedAt").isNull()); // Chỉ lấy sản phẩm chưa xóa
//                predicates.add(root.get("isDeleted").isFalse()); // Chỉ lấy sản phẩm chưa xóa
                query.distinct(true); // Tránh duplicate khi join
            }

//            Join<Product, ProductResource> productProductResourceJoin = root.join("productResources").;


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


//
}



