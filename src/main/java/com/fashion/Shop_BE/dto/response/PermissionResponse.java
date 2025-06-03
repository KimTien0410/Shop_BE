package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    private String permissionName;
    private String permissionDescription;

}
