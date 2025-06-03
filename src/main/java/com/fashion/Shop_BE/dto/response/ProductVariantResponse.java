package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponse {
    private Long variantId;
    private String color;
    private String size;
    private int stockQuantity;
    private Double variantPrice;

}
