package com.fashion.Shop_BE.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPermission {
    private String permissionName;
    private String permissionDescription;
}
