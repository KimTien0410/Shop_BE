package com.fashion.Shop_BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_resources")
public class ProductResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_resource_id")
    private Long resourceId;
    @Column(name="resource_name", nullable=false)
    private String resourceName;
    @Column(name="resource_url",nullable=false)
    private String resourceUrl;
    @Column(name="resource_type", nullable=false)
    private String resourceType;
    @Column(name="is_primary", nullable=false)
    private boolean isPrimary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable=false)
    @JsonIgnore
    private Product product;
    @PrePersist
    public void prePersist() {
        if (resourceType == null) resourceType = "IMAGE";
    }
}
