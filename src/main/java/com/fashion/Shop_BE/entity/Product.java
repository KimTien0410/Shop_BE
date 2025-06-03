package com.fashion.Shop_BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@NamedEntityGraph(
        name = "Product.details",
        attributeNodes = {
                @NamedAttributeNode("brand"),
                @NamedAttributeNode("category"),
                @NamedAttributeNode("productVariants"),
                @NamedAttributeNode("productResources")
        }
)
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long productId;

    @Column(name="product_name",nullable=false)
    private String productName;

    @Column(name="product_slug",unique=true,nullable=false)
    private String productSlug;

    @Column(name="product_description")
    private String productDescription;

    @Column(name="produc_rating")
    private Double productRating;

    @Column(name="product_sold")
    private int productSold;

    @Column(name="product_quantity")
    private int productQuantity;

    @Column(name="base_price")
    private Double basePrice;

    @Column(name="discount_price")
    private Double discountPrice;

    @Column(name="is_new_arrival",nullable = false)
    private boolean isNewArrival;

    @Column(name="is_deleted")
    private boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at",nullable = false,updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at",nullable=true)
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deleted_at",nullable=true)
    private Date deletedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProductVariant> productVariants;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProductResource> productResources;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id",nullable = false)
    @JsonIgnore
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",nullable = false)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews;


    @PrePersist
    protected void onCreate() {
        createdAt =  new Date();
        updatedAt = new Date();
        isDeleted = false;
        isNewArrival=true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}
