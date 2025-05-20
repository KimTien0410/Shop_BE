package com.fashion.Shop_BE.dto.response;

import com.fashion.Shop_BE.enums.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailResponse {
    private Long cartDetailId;
    private Long productVariantId;
    private String productName;
    private String color;
    private Size size;
    private String productImage;
    private Double price;
    private int quantity;
    private Double subTotal;
}
