package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestBrand;
import com.fashion.Shop_BE.dto.response.BrandResponse;
import com.fashion.Shop_BE.entity.Brand;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T17:14:18+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class BrandMapperImpl implements BrandMapper {

    @Override
    public Brand toBrand(RequestBrand requestAddBrand) {
        if ( requestAddBrand == null ) {
            return null;
        }

        Brand.BrandBuilder brand = Brand.builder();

        brand.brandName( requestAddBrand.getBrandName() );
        brand.brandDescription( requestAddBrand.getBrandDescription() );

        return brand.build();
    }

    @Override
    public void updateBrand(Brand brand, RequestBrand requestAddBrand) {
        if ( requestAddBrand == null ) {
            return;
        }

        brand.setBrandName( requestAddBrand.getBrandName() );
        brand.setBrandDescription( requestAddBrand.getBrandDescription() );
    }

    @Override
    public BrandResponse toBrandResponse(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandResponse.BrandResponseBuilder brandResponse = BrandResponse.builder();

        brandResponse.brandId( brand.getBrandId() );
        brandResponse.brandName( brand.getBrandName() );
        brandResponse.brandCode( brand.getBrandCode() );
        brandResponse.brandThumbnail( brand.getBrandThumbnail() );
        brandResponse.brandDescription( brand.getBrandDescription() );
        brandResponse.createdAt( brand.getCreatedAt() );
        brandResponse.updatedAt( brand.getUpdatedAt() );
        brandResponse.deletedAt( brand.getDeletedAt() );

        return brandResponse.build();
    }
}
