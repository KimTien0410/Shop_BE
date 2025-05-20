package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestCategory;
import com.fashion.Shop_BE.dto.response.CategoryResponse;
import com.fashion.Shop_BE.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryThumbnail",ignore = true)
    @Mapping(target = "parentCategory",ignore = true)
    @Mapping(target="subCategories",ignore = true)
    @Mapping(target="products",ignore = true)
    Category requestToCategory(RequestCategory requestCategory);

    @Mapping(target = "categoryThumbnail",ignore = true)
    @Mapping(target = "parentCategory",ignore = true)
    @Mapping(target="subCategories",ignore = true)
    @Mapping(target="products",ignore = true)
    void updateCategory(@MappingTarget Category category, RequestCategory requestCategory);

    CategoryResponse categoryToCategoryResponse(Category category);

}
