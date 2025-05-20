package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.enums.DiscountType;
import com.fashion.Shop_BE.validation.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestVoucher {
    @NotBlank(message="VOUCHER_CODE_NOT_BLANK")
    private String voucherCode;
    @NotBlank(message="DISCOUNT_TYPE_NOT_BLANK")
    @ValidEnum(enumClass = DiscountType.class)
    private String discountType;
    @NotNull(message="DISCOUNT_VALUE_NOT_NULL")
    @Min(value=0,message="DISCOUNT_VALUE_BIGGER_THAN_0")
    private Double discountValue;
    @Min(value=0,message="MIN_ORDER_VALUE_BIGGER_THAN_0")
    private Double minOrderValue;
    @Min(value=0, message="MAX_ORDER_VALUE_BIGGER_THAN_0")
    private Double maxOrderValue;
    @NotNull(message="START_DATE_NOT_NULL")
    @FutureOrPresent(message="START_DATE_INVALID")
    private Date startDate;
    @NotNull(message="END_DATE_NOT_NULL")
    @Future(message="END_DATE_INVALID")
    private Date endDate;
    @Min(value=0,message = "QUANTITY_INVALID")
    private int quantity;
}
