package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
    @Query("SELECT b FROM Brand b WHERE (LOWER(b.brandName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(b.brandCode) LIKE LOWER(CONCAT('%', :search, '%'))) and b.deletedAt IS NULL")
    Page<Brand> searchBrands(@Param("search") String search, Pageable pageable);
    @Query("SELECT b FROM Brand b WHERE b.brandName = :brandName and b.deletedAt IS NULL")
    Optional<Brand> findByBrandName(@Param("brandName")String brandName);
    @Query("SELECT b FROM Brand b WHERE b.brandCode = :brandCode and b.deletedAt IS NULL")
    Optional<Brand> findByBrandCode(String brandCode);
    @Query("SELECT b FROM Brand b WHERE b.deletedAt IS NULL")
    Page<Brand> findAllByDeletedAtIsNull(Pageable pageable);

    // Admin
    @Query("""
    SELECT b FROM Brand b 
    WHERE (:search IS NULL OR b.brandName LIKE %:search% OR b.brandCode LIKE %:search%)
    """)
    Page<Brand> findAllBrands(@Param("search") String search,Pageable pageable);

    @Modifying
    @Query("UPDATE Brand b SET b.deletedAt = NULL WHERE b.brandId = :id")
    void restoreBrand(@Param("id") Long id);



}
