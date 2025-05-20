package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.entity.Brand;
import com.fashion.Shop_BE.validation.UniqueField;
import com.fashion.Shop_BE.validation.ValidFile;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestBrand {
    @NotBlank(message="BRAND_NAME_UNVALID")
    private String brandName;
    @ValidFile(message="FILE_INVALID")
    private MultipartFile brandThumbnail;
    private String brandDescription;
}
