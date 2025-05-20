package com.fashion.Shop_BE.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherResponse {
    private Long voucherId;
    private String voucherCode;
    private String discountType;
    private Double discountValue;
    private Double minOrderValue;
    private Double maxOrderValue;
    private int quantity;
    private Date startDate;
    private Date endDate;

}
