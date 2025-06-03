package com.fashion.Shop_BE.dto.response;

import com.fashion.Shop_BE.enums.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long orderDetailId;
    private Long productVariantId;
    private Long productId;
    private String productName;
    private String productImage;
    private String color;
    private Size size;
    private int quantity;
    private double price;
    private double totalPrice;

}
