package com.fashion.Shop_BE.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductResponse {
    private Long productId;
    private String productName;
    private String productSlug;
    private String productDescription;
    private int productSold;
    private int productQuantity;
    private Double basePrice;
    private boolean isNewArrival;
    private BrandResponse brand;
    private CategoryResponse category;
    private Set<ProductResourceResponse> productResources ;
    private double averageRating;

    private Set<ProductVariantResponse> productVariants ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Date deletedAt;

}
