package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResourceResponse {
    private Long resourceId;
    private String resourceName;
    private String resourceUrl;
    private String resourceType;
    private boolean isPrimary;
}
