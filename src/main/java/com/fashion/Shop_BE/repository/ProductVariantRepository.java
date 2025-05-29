package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.dto.response.ProductItemResponse;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProduct(Product product);
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.product.productId = :productId and pv.color= :color and pv.size = :size")
    ProductVariant findByProductAndColorAndSize(@Param("productId")Long productId,@Param("color") String color,@Param("size") String size);
    @Query("SELECT DISTINCT pv.color FROM ProductVariant pv WHERE pv.product.productId = :productId")
    Set<String> findAllColorsByProduct(@Param("productId") Long productId);
    @Query("SELECT DISTINCT pv.size FROM ProductVariant pv WHERE pv.product.productId = :productId")
    Set<String> findAllSizesByProduct(@Param("productId") Long productId);

//    @Query("SELECT pv.product FROM ProductVariant pv WHERE pv.variantId = :variantId")
//    Product findProductByVariant(@Param("variantId") Long variantId);

    @Query("""
    SELECT new com.fashion.Shop_BE.dto.response.ProductItemResponse(
        p.productId,
        p.productName,
        p.productSlug,
        p.productSold,
        pv.variantPrice,
        pr.resourceUrl,
        p.category.categoryId,
        p.brand.brandId
        
    )
    FROM ProductVariant pv
    JOIN pv.product p
    LEFT JOIN p.productResources pr ON pr.isPrimary = true
    WHERE pv.variantId = :variantId
""")
    ProductItemResponse findProductItemResponseByVariantId(@Param("variantId") Long variantId);

}
