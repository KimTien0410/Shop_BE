package com.fashion.Shop_BE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="delivery_methods")
public class DeliveryMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_method_id")
    private Long deliveryMethodId;
    @Column(name="delivery_method_name", nullable=false)
    private String deliveryMethodName;
    @Column(name="delivery_fee",nullable=false)
    private Double deliveryFee;
    @Column(name="delivery_method_logo",nullable=true)
    private String deliveryMethodLogo;
    @Column(name="delivery_method_description",nullable=true)
    private String deliveryMethodDescription;
    @Column(name="delivery_is_active",nullable = false)
    private boolean deliveryIsActive=true;

    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @OneToMany(mappedBy = "deliveryMethod")
    private List<Order> orders;
}
