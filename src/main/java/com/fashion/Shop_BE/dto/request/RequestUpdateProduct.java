package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.validation.UniqueField;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RequestUpdateProduct {
    @NotBlank(message="PRODUCT_NAME_NOT_BLANK")
    @UniqueField(entity = Product.class,idField = "productId",fieldName = "productName",message="PRODUCT_NAME_EXISTED")
    private String productName;
    private String productDescription;
    @JsonProperty("isNewArrival")
    private boolean isNewArrival;
    @NotNull(message="BRAND_ID_NOT_NULL")
    private Long brandId;
    @NotNull(message="CATEGORY_ID_NOT_NULL")
    private Long categoryId;

    private List<RequestUpdateProductVariant> productVariants = new ArrayList<>();
    private List<MultipartFile> images = new ArrayList<>();
}
