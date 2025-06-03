package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.*;
import com.fashion.Shop_BE.dto.response.ProductResourceResponse;
import com.fashion.Shop_BE.dto.response.ProductResponse;
import com.fashion.Shop_BE.dto.response.ProductVariantResponse;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductResource;
import com.fashion.Shop_BE.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    //Product
    @Mapping(source="newArrival", target="isNewArrival")
    Product requestToProduct(RequestProduct requestProduct);


    @Mapping(target = "productVariants", ignore = true)
    void updateProduct(@MappingTarget Product product, RequestUpdateProduct requestProduct);
    @Mapping(source="newArrival", target="isNewArrival")
    ProductResponse productToProductResponse(Product product);

    List<Product> toProductResponseList(List<Product> products);



    //ProductVariant
    ProductVariant requestToProductVariant(RequestProductVariant requestProductVariant);
    List<ProductVariant> requestToProductVariantList(List<RequestProductVariant> requestProductVariants);
    Set<ProductVariant> requestToProductVariantSet(List<RequestProductVariant> requestProductVariants);
    ProductVariant requestUpdateToProductVariant(RequestUpdateProductVariant requestProductVariant);


    ProductVariantResponse variantToProductVariantResponse(ProductVariant productVariant);

    List<ProductVariantResponse> variantToProductVariantResponseList(Set<ProductVariant> productVariants);
    Set<ProductVariantResponse> variantToProductVariantResponseSet(List<ProductVariant> productVariants);

    void updateProductVariant(@MappingTarget ProductVariant productVariant, RequestUpdateProductVariant requestProductVariant);

    //ProductResource
    @Mapping(source="primary", target="isPrimary")
    ProductResourceResponse toProductResourceResponse(ProductResource productResource);

    List<ProductResourceResponse> toProductResourceResponseList(List<ProductResource> productResources);
}
