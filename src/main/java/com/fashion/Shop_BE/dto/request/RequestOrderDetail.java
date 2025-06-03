package com.fashion.Shop_BE.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderDetail {
    @NotNull(message="PRODUCT_VARIANT_ID_REQUIRED")
    private Long productVariantId;
    @Min(value=1,message="QUANTITY_INVALID")
    private int quantity;

}
