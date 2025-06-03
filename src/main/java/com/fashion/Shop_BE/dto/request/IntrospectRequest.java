package com.fashion.Shop_BE.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectRequest {
    private String token;
}
