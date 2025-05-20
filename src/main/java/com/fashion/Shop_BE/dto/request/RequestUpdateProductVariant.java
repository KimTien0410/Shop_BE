package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.enums.Size;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateProductVariant {
//    @NotBlank(message="VARIANT_ID_NOT_BLANK")
    private Long variantId;
    @NotBlank(message = "COLOR_NOT_BLANK")
    private String color;
    @NotNull(message="SIZE_NOT_BLANK")
    @Enumerated(EnumType.STRING)
    private Size size;
    private int stockQuantity;
    private double variantPrice;
}
