package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.enums.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFilterRequest {
    private String search;
    private Long categoryId;
    private Set<String> brandCode;
    private Set<String> color;
    private Set<Size> size;
    private Double minPrice;
    private Double maxPrice;
    private Boolean isNewArrival;
    private Boolean isBestSeller;
}
