package com.fashion.Shop_BE.dto.request;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAddPermissionRoRole {
   //private String roleDescription;
    private Set<String> permissions;
}
