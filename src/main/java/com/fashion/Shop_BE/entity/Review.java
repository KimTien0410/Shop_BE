package com.fashion.Shop_BE.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable=false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable=false)
    private Product product;
    @Column(name="review_rating",nullable=false)
    private int reviewRating;
    @Column(name="review_comment",nullable=true)
    private String reviewComment;
    @Column(name="is_show",nullable=false)
    private boolean isShow=true;
}
