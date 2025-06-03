package com.fashion.Shop_BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="receiver_addresses")
public class ReceiverAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private Long addressId;
    @Column(name="receiver_name",nullable=false)
    private String receiverName;
    @Column(name="receiver_phone",nullable=false)
    private String receiverPhone;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String ward;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable=false)
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @Column(name="is_default",nullable=false)
    private boolean isDefault=false;

    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
}
