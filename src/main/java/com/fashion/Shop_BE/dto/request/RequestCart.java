package com.fashion.Shop_BE.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCart {
    private Long cartDetailId;
    @NotNull(message="PRODUCT_VARIANT_ID_REQUIRED")
    private Long productVariantId;
    @Min(value=1,message="CART_QUANTITY_INVALID")
    private Integer quantity;
}
