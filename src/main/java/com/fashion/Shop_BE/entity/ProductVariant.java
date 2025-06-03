package com.fashion.Shop_BE.entity;

import com.fashion.Shop_BE.enums.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_variants",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "color", "size"})
})
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_variant_id")
    private Long variantId;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Size size;
    @Column(name="stock_quantity", nullable = false)
    private int stockQuantity;
    @Column(name="variant_price", nullable = false)
    private Double variantPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable = false)
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<CartDetail> cartDetails=new ArrayList<>();

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails=new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
