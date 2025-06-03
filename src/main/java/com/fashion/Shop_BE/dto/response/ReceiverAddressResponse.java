package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiverAddressResponse {
    private Long addressId;
    private String receiverName;
    private String receiverPhone;
    private String street;
    private String ward;
    private String district;
    private String city;
    private boolean isDefault ;

//    private Long userId;
}
