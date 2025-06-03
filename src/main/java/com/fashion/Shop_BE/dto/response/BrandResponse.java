package com.fashion.Shop_BE.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    private Long brandId;
    private String brandName;
    private String brandCode;
    private String brandThumbnail;
    private String brandDescription;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

}
