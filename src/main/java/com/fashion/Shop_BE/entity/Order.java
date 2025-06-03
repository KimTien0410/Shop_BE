package com.fashion.Shop_BE.entity;

import com.fashion.Shop_BE.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
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
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable=false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id",nullable=false)
    private ReceiverAddress receiverAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="payment_method_id",nullable=false)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_method_id",nullable=false)
    private DeliveryMethod deliveryMethod;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="voucher_id",nullable=true)
    private Voucher voucher;

    @Column(name="order_total_amount",nullable=false)
    private Double orderTotalAmount;

    @Column(name="order_discount",nullable=true)
    private Double orderDiscount;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status",nullable=false)
    private OrderStatus orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_date")
    private Date orderDate;

    @Column(name="shipment_tracking_numer",nullable=true)
    private String shipmentTrackingNumber;
    @Column(name="expected_delivery_date",nullable=true)
    private Date expectedDeliveryDate;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="order",cascade=CascadeType.ALL)
    @ToString.Exclude
    private List<OrderDetail> orderDetails;

    @OneToOne(fetch = FetchType.LAZY,mappedBy="order",cascade = CascadeType.ALL)
    @ToString.Exclude
    private Payment payment;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderUpdatedAt;

    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

}
