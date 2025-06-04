package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestProduct;
import com.fashion.Shop_BE.dto.request.RequestProductVariant;
import com.fashion.Shop_BE.dto.request.RequestUpdateProduct;
import com.fashion.Shop_BE.dto.request.RequestUpdateProductVariant;
import com.fashion.Shop_BE.dto.response.BrandResponse;
import com.fashion.Shop_BE.dto.response.CategoryResponse;
import com.fashion.Shop_BE.dto.response.ProductResourceResponse;
import com.fashion.Shop_BE.dto.response.ProductResponse;
import com.fashion.Shop_BE.dto.response.ProductVariantResponse;
import com.fashion.Shop_BE.entity.Brand;
import com.fashion.Shop_BE.entity.Category;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductResource;
import com.fashion.Shop_BE.entity.ProductVariant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-29T20:44:09+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product requestToProduct(RequestProduct requestProduct) {
        if ( requestProduct == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.isNewArrival( requestProduct.isNewArrival() );
        product.productName( requestProduct.getProductName() );
        product.productDescription( requestProduct.getProductDescription() );
        product.productVariants( requestToProductVariantSet( requestProduct.getProductVariants() ) );

        return product.build();
    }

    @Override
    public void updateProduct(Product product, RequestUpdateProduct requestProduct) {
        if ( requestProduct == null ) {
            return;
        }

        product.setProductName( requestProduct.getProductName() );
        product.setProductDescription( requestProduct.getProductDescription() );
        product.setNewArrival( requestProduct.isNewArrival() );
    }

    @Override
    public ProductResponse productToProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.isNewArrival( product.isNewArrival() );
        productResponse.productId( product.getProductId() );
        productResponse.productName( product.getProductName() );
        productResponse.productSlug( product.getProductSlug() );
        productResponse.productDescription( product.getProductDescription() );
        productResponse.productSold( product.getProductSold() );
        productResponse.productQuantity( product.getProductQuantity() );
        productResponse.basePrice( product.getBasePrice() );
        productResponse.brand( brandToBrandResponse( product.getBrand() ) );
        productResponse.category( categoryToCategoryResponse( product.getCategory() ) );
        productResponse.productResources( productResourceSetToProductResourceResponseSet( product.getProductResources() ) );
        productResponse.productVariants( productVariantSetToProductVariantResponseSet( product.getProductVariants() ) );
        if ( product.getCreatedAt() != null ) {
            productResponse.createdAt( LocalDateTime.ofInstant( product.getCreatedAt().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( product.getUpdatedAt() != null ) {
            productResponse.updatedAt( LocalDateTime.ofInstant( product.getUpdatedAt().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        productResponse.deletedAt( product.getDeletedAt() );

        return productResponse.build();
    }

    @Override
    public List<Product> toProductResponseList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( products.size() );
        for ( Product product : products ) {
            list.add( product );
        }

        return list;
    }

    @Override
    public ProductVariant requestToProductVariant(RequestProductVariant requestProductVariant) {
        if ( requestProductVariant == null ) {
            return null;
        }

        ProductVariant.ProductVariantBuilder productVariant = ProductVariant.builder();

        productVariant.color( requestProductVariant.getColor() );
        productVariant.size( requestProductVariant.getSize() );
        productVariant.stockQuantity( requestProductVariant.getStockQuantity() );
        productVariant.variantPrice( requestProductVariant.getVariantPrice() );

        return productVariant.build();
    }

    @Override
    public List<ProductVariant> requestToProductVariantList(List<RequestProductVariant> requestProductVariants) {
        if ( requestProductVariants == null ) {
            return null;
        }

        List<ProductVariant> list = new ArrayList<ProductVariant>( requestProductVariants.size() );
        for ( RequestProductVariant requestProductVariant : requestProductVariants ) {
            list.add( requestToProductVariant( requestProductVariant ) );
        }

        return list;
    }

    @Override
    public Set<ProductVariant> requestToProductVariantSet(List<RequestProductVariant> requestProductVariants) {
        if ( requestProductVariants == null ) {
            return null;
        }

        Set<ProductVariant> set = new LinkedHashSet<ProductVariant>( Math.max( (int) ( requestProductVariants.size() / .75f ) + 1, 16 ) );
        for ( RequestProductVariant requestProductVariant : requestProductVariants ) {
            set.add( requestToProductVariant( requestProductVariant ) );
        }

        return set;
    }

    @Override
    public ProductVariant requestUpdateToProductVariant(RequestUpdateProductVariant requestProductVariant) {
        if ( requestProductVariant == null ) {
            return null;
        }

        ProductVariant.ProductVariantBuilder productVariant = ProductVariant.builder();

        productVariant.variantId( requestProductVariant.getVariantId() );
        productVariant.color( requestProductVariant.getColor() );
        productVariant.size( requestProductVariant.getSize() );
        productVariant.stockQuantity( requestProductVariant.getStockQuantity() );
        productVariant.variantPrice( requestProductVariant.getVariantPrice() );

        return productVariant.build();
    }

    @Override
    public ProductVariantResponse variantToProductVariantResponse(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }

        ProductVariantResponse.ProductVariantResponseBuilder productVariantResponse = ProductVariantResponse.builder();

        productVariantResponse.variantId( productVariant.getVariantId() );
        productVariantResponse.color( productVariant.getColor() );
        if ( productVariant.getSize() != null ) {
            productVariantResponse.size( productVariant.getSize().name() );
        }
        productVariantResponse.stockQuantity( productVariant.getStockQuantity() );
        productVariantResponse.variantPrice( productVariant.getVariantPrice() );

        return productVariantResponse.build();
    }

    @Override
    public List<ProductVariantResponse> variantToProductVariantResponseList(Set<ProductVariant> productVariants) {
        if ( productVariants == null ) {
            return null;
        }

        List<ProductVariantResponse> list = new ArrayList<ProductVariantResponse>( productVariants.size() );
        for ( ProductVariant productVariant : productVariants ) {
            list.add( variantToProductVariantResponse( productVariant ) );
        }

        return list;
    }

    @Override
    public Set<ProductVariantResponse> variantToProductVariantResponseSet(List<ProductVariant> productVariants) {
        if ( productVariants == null ) {
            return null;
        }

        Set<ProductVariantResponse> set = new LinkedHashSet<ProductVariantResponse>( Math.max( (int) ( productVariants.size() / .75f ) + 1, 16 ) );
        for ( ProductVariant productVariant : productVariants ) {
            set.add( variantToProductVariantResponse( productVariant ) );
        }

        return set;
    }

    @Override
    public void updateProductVariant(ProductVariant productVariant, RequestUpdateProductVariant requestProductVariant) {
        if ( requestProductVariant == null ) {
            return;
        }

        productVariant.setVariantId( requestProductVariant.getVariantId() );
        productVariant.setColor( requestProductVariant.getColor() );
        productVariant.setSize( requestProductVariant.getSize() );
        productVariant.setStockQuantity( requestProductVariant.getStockQuantity() );
        productVariant.setVariantPrice( requestProductVariant.getVariantPrice() );
    }

    @Override
    public ProductResourceResponse toProductResourceResponse(ProductResource productResource) {
        if ( productResource == null ) {
            return null;
        }

        ProductResourceResponse.ProductResourceResponseBuilder productResourceResponse = ProductResourceResponse.builder();

        productResourceResponse.isPrimary( productResource.isPrimary() );
        productResourceResponse.resourceId( productResource.getResourceId() );
        productResourceResponse.resourceName( productResource.getResourceName() );
        productResourceResponse.resourceUrl( productResource.getResourceUrl() );
        productResourceResponse.resourceType( productResource.getResourceType() );

        return productResourceResponse.build();
    }

    @Override
    public List<ProductResourceResponse> toProductResourceResponseList(List<ProductResource> productResources) {
        if ( productResources == null ) {
            return null;
        }

        List<ProductResourceResponse> list = new ArrayList<ProductResourceResponse>( productResources.size() );
        for ( ProductResource productResource : productResources ) {
            list.add( toProductResourceResponse( productResource ) );
        }

        return list;
    }

    protected BrandResponse brandToBrandResponse(Brand brand) {
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

    protected CategoryResponse categoryToCategoryResponse(Category category) {
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

    protected Set<ProductResourceResponse> productResourceSetToProductResourceResponseSet(Set<ProductResource> set) {
        if ( set == null ) {
            return null;
        }

        Set<ProductResourceResponse> set1 = new LinkedHashSet<ProductResourceResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ProductResource productResource : set ) {
            set1.add( toProductResourceResponse( productResource ) );
        }

        return set1;
    }

    protected Set<ProductVariantResponse> productVariantSetToProductVariantResponseSet(Set<ProductVariant> set) {
        if ( set == null ) {
            return null;
        }

        Set<ProductVariantResponse> set1 = new LinkedHashSet<ProductVariantResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ProductVariant productVariant : set ) {
            set1.add( variantToProductVariantResponse( productVariant ) );
        }

        return set1;
    }
}
