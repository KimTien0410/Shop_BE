package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.validation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestReceiverAddress {
    @NotBlank(message="RECEIVER_NAME_NOT_BLANK")
    private String receiverName;
    @PhoneNumber(message="PHONE_INVALID")
    private String receiverPhone;
    @NotBlank(message="STREET_NOT_BLANK")
    private String street;
    @NotBlank(message="WARD_NOT_BLANK")
    private String ward;
    @NotBlank(message="DISTRICT_NOT_BLANK")
    private String district;
    @NotBlank(message="CITY_NOT_BLANK")
    private String city;

}
