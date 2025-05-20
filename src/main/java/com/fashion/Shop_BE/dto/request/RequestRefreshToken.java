package com.fashion.Shop_BE.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestRefreshToken {
    private String token;
}
