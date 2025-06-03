package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentCategoryIsNullAndDeletedAtIsNull();
    Optional<Category> findByCategoryCodeAndDeletedAtIsNull(String categoryCode);
    Optional<Category> findByCategoryIdAndDeletedAtIsNull(Long categoryId);
    @Query("SELECT c FROM Category c WHERE c.parentCategory.categoryId = :parentId OR c.categoryId = :parentId")
    List<Category> findAllByParentCategoryId(@Param("parentId") Long parentId);
    @Query("SELECT c FROM Category c WHERE c.categoryName like %:search% ")
    Page<Category> findByParentCategoryIsNull(String search,Pageable pageable);

    Page<Category> findByParentCategoryIsNull(Pageable pageable);

}
