package com.fashion.Shop_BE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_method_id")
    private Long paymentMethodId;
    @Column(name="payment_method_name",nullable=false)
    private String paymentMethodName;
    @Column(name="payment_method_logo")
    private String paymentMethodLogo;
    @Column(name="payment_is_active",nullable=false)
    private boolean paymentIsActive=true;

    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

}
