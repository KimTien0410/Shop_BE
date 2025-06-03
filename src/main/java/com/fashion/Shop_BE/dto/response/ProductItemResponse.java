package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItemResponse {
    private Long productId;
    private String productName;
    private String productSlug;
    private int productSold;
    private Double basePrice;
    private String thumbnail;
    private Long categoryId;
    private Long brandId;
//    private double averageRating;
}
