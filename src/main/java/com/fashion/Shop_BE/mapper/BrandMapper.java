package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestBrand;
import com.fashion.Shop_BE.dto.response.BrandResponse;
import com.fashion.Shop_BE.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target="brandId",ignore = true)
    @Mapping(target = "brandThumbnail", ignore = true)
    Brand toBrand(RequestBrand requestAddBrand);

    @Mapping(target="brandId",ignore = true)
    @Mapping(target = "brandThumbnail", ignore = true)
    void updateBrand(@MappingTarget Brand brand, RequestBrand requestAddBrand);

    BrandResponse toBrandResponse(Brand brand);
}
