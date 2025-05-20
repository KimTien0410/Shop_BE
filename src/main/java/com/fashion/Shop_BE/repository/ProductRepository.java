package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.dto.response.ProductItemResponse;
import com.fashion.Shop_BE.entity.Brand;
import com.fashion.Shop_BE.entity.Category;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.specification.ProductSpecification;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//JpaSpecificationExecutor<Product>
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(value = "Product.details", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT p FROM Product p WHERE p.productSlug = :productSlug AND p.deletedAt IS NULL")
    Optional<Product> findByProductSlug(@Param("productSlug") String productSlug);

    @EntityGraph(value = "Product.details", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT p FROM Product p WHERE p.productId = :id AND p.deletedAt IS NULL")
    Optional<Product> findByProductIdAndDeletedAtIsNotNull(Long id);

    @Query("SELECT p FROM Product p " +
            "WHERE (:categoryId IS NULL OR p.category.categoryId = :categoryId) " +
            "AND (:brandId IS NULL OR p.brand.brandId = :brandId) " +
            "AND (:search IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:isNew = false OR p.isNewArrival = true) " +
            "AND p.deletedAt IS NULL " +
            "ORDER BY p.createdAt DESC")
    Page<Product> findAllWithFilters(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("search") String search,
            @Param("isNew") boolean isNew,
            Pageable pageable
    );



//        @Query("""
//        SELECT new com.fashion.Shop_BE.dto.response.ProductItemResponse(
//            p.productId,
//            p.productName,
//            p.productSlug,
//            p.productSold,
//            p.basePrice,
//            pr.resourceUrl,
//            p.category.categoryId
//        )
//        FROM Product p
//        LEFT JOIN p.productResources pr ON pr.isPrimary = true
//        WHERE p.basePrice IS NOT NULL and p.deletedAt IS NULL
//    """)
//    Page<ProductItemResponse> findAllProductItems(Pageable pageable);
//



    Page<Product> findAll(Specification<Product> spec, Pageable pageable);


    Page<Product> findAllByIsNewArrivalAndDeletedAtIsNull(boolean isNew, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL ORDER BY p.productSold DESC")
    Page<Product> findAllBestSellers(Pageable pageable);


    @Query("SELECT p FROM Product p WHERE p.category.categoryId IN :categoryIds AND p.deletedAt IS NULL")
    Page<Product> findAllByCategoryIds(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productName like %:search% ")
    Page<Product> findAllBySearch(String search,Pageable pageable);

    @Query("Select count(*) from Product p where p.deletedAt IS NULL")
    Long countAllProducts();

    

}
