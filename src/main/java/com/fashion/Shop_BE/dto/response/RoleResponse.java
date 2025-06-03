package com.fashion.Shop_BE.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private String roleName;
    private String roleDescription;
    private Set<PermissionResponse> permissions ;
}
