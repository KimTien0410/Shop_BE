package com.fashion.Shop_BE.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestRole {
    @NotBlank(message="ROLE_NAME_REQUIRED")
    private String roleName;
    private String roleDescription;
    private Set<String> permissions;
}
