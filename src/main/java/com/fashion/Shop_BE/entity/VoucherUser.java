package com.fashion.Shop_BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="voucher_users")
public class VoucherUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="voucher_user_id")
    private Long voucherUserId;

    @ManyToOne()
    @JoinColumn(name="user_id",nullable=false)
    @JsonIgnore
    private User user;

    @ManyToOne()
    @JoinColumn(name="voucher_id",nullable = false)
    @JsonIgnore
    private Voucher voucher;

    @Column(name="is_used",nullable=false)
    private boolean isUsed=false;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="used_at",nullable=false)
    private Date usedAt;





}
