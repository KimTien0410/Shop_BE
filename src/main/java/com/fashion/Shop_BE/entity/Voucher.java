package com.fashion.Shop_BE.entity;

import com.fashion.Shop_BE.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="voucher_id")
    private Long voucherId;
    @Column(name="voucher_code",unique=true,nullable=false)
    private String voucherCode;
    @Enumerated(EnumType.STRING)
    @Column(name="discount_type",nullable=false)
    private DiscountType discountType;
    @Column(name="discount_value",nullable=false)
    private Double discountValue;
    @Column(name="min_order_value")
    private Double minOrderValue;
    @Column(name="max_order_value")
    private Double maxOrderValue;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_date",nullable=false)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_date",nullable=false)
    private Date endDate;
    @Column(name="quantity",nullable=false)
    private int quantity;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date updatedAt;

    @Column(name="is_active",nullable=false)
    private boolean isActive=true;
    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

}
