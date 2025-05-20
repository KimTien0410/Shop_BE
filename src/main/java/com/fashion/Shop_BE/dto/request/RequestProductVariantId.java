package com.fashion.Shop_BE.dto.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestProductVariantId {
    List<Long> productVariantIds;
}
