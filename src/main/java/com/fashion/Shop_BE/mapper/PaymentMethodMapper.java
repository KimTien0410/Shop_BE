package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestPaymentMethod;
import com.fashion.Shop_BE.dto.response.PaymentMethodResponse;
import com.fashion.Shop_BE.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    @Mapping(target = "paymentMethodLogo", ignore = true)
    PaymentMethod toPaymentMethod(RequestPaymentMethod paymentMethod);

    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);

    @Mapping(target = "paymentMethodLogo", ignore = true)
    void updatePaymentMethod(@MappingTarget PaymentMethod paymentMethod, RequestPaymentMethod requestPaymentMethod);
}
