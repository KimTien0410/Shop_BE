package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestCategory;
import com.fashion.Shop_BE.dto.response.CategoryResponse;
import com.fashion.Shop_BE.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-29T20:44:09+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category requestToCategory(RequestCategory requestCategory) {
        if ( requestCategory == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.categoryName( requestCategory.getCategoryName() );
        category.categoryDescription( requestCategory.getCategoryDescription() );

        return category.build();
    }

    @Override
    public void updateCategory(Category category, RequestCategory requestCategory) {
        if ( requestCategory == null ) {
            return;
        }

        category.setCategoryName( requestCategory.getCategoryName() );
        category.setCategoryDescription( requestCategory.getCategoryDescription() );
    }

    @Override
    public CategoryResponse categoryToCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setCategoryId( category.getCategoryId() );
        categoryResponse.setCategoryName( category.getCategoryName() );
        categoryResponse.setCategoryCode( category.getCategoryCode() );
        categoryResponse.setCategoryThumbnail( category.getCategoryThumbnail() );
        categoryResponse.setCategoryDescription( category.getCategoryDescription() );
        categoryResponse.setDeletedAt( category.getDeletedAt() );

        return categoryResponse;
    }
}
