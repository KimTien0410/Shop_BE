package com.fashion.Shop_BE.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="brand_id")
    private Long brandId;
    @Column(name="brand_name",unique = true)
    private String brandName;
    @Column(name="brand_code",unique = true)
    private String brandCode;
    @Column(name="brand_thumbnail")
    private String brandThumbnail;
    @Column(name="brand_description")
    private String brandDescription;

    @Column(name="created_at",nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name="updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @OneToMany(mappedBy = "brand",fetch=FetchType.LAZY)
    private List<Product> products=new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.deletedAt = null;
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
