package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.entity.Category;
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
public class RequestCategory {
    @NotBlank(message="CATEGORY_NAME_NOT_BLANK")
    @UniqueField(idField = "categoryId",entity = Category.class,fieldName = "categoryName",message="CATEGORY_NAME_EXISTED")
    private String categoryName;
    @ValidFile(message="FILE_UNVALID")
    private MultipartFile categoryThumbnail;
    private String categoryDescription;
    private Long parentId;
}
