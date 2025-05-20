package com.fashion.Shop_BE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name="categories")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long categoryId;
    @Column(name="category_name",nullable=false)
    private String categoryName;
    @Column(name="category_code",unique = true,nullable=false)
    private String categoryCode;
    @Column(name="category_thumbnail")
    private String categoryThumbnail;
    @Column(name="category_description",nullable=true)
    private String categoryDescription;
//    Quan hệ với chính nó(Catgory cha)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parentCategory;

    // Quan hệ với danh sách danh mục con
    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Category> subCategories=new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Product> products=new ArrayList<>();

    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    public Category() {
    }

    public Category(String categoryName, Category parentCategory) {
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
    }

}
