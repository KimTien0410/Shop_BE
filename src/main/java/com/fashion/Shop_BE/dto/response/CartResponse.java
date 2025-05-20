package com.fashion.Shop_BE.dto.response;

import com.fashion.Shop_BE.entity.ProductVariant;
import com.fashion.Shop_BE.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long cartId;
    private Long userId;
    private Double totalPrice;
    private int totalQuantity;
    private List<CartDetailResponse> cartDetailResponses;
}
