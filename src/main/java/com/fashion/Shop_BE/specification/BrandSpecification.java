package com.fashion.Shop_BE.specification;

import com.fashion.Shop_BE.entity.Brand;
import com.fashion.Shop_BE.enums.EntityStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class BrandSpecification {
    public static Specification<Brand> withFilters(String search, EntityStatus status) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //Tìm kiếm theo tên hoặc mã thương hiệu
            if (search != null && !search.isBlank()) {
                Predicate namePredicate = cb.like(root.get("brandName"), "%" + search + "%");
                Predicate codePredicate = cb.like(root.get("brandCode"), "%" + search + "%");
                predicates.add(cb.or(namePredicate, codePredicate));
            }
            //Filter theo trạng thái
            if (status == EntityStatus.DELETED) {
                predicates.add(cb.isNotNull(root.get("deletedAt")));
            } else if (status == EntityStatus.ACTIVE) {
                predicates.add(cb.isNull(root.get("deletedAt")));
            }
            // Trả về tập điều kieện kết hợp bằng AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
