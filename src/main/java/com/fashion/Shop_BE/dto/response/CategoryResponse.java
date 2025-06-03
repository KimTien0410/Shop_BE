package com.fashion.Shop_BE.dto.response;

import com.fashion.Shop_BE.entity.Category;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private String categoryThumbnail;
    private Long parentId;
    private String categoryDescription;
    private Date deletedAt;
    private List<CategoryResponse> children=new ArrayList<>();
    public CategoryResponse(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.categoryCode = category.getCategoryCode();
        this.categoryThumbnail = category.getCategoryThumbnail();
        this.categoryDescription = category.getCategoryDescription();
        this.deletedAt = category.getDeletedAt();
        this.parentId=(category.getParentCategory() !=null) ? category.getParentCategory().getCategoryId() : null;
    }
}
