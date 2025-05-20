package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestDeliveryMethod;
import com.fashion.Shop_BE.dto.response.DeliveryMethodResponse;
import com.fashion.Shop_BE.entity.DeliveryMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeliveryMethodMapper {
    @Mapping(target = "deliveryMethodLogo", ignore = true)
    DeliveryMethod toDeliveryMethod(RequestDeliveryMethod deliveryMethod);

    DeliveryMethodResponse toDeliveryMethodResponse(DeliveryMethod deliveryMethod);
    @Mapping(target = "deliveryMethodLogo", ignore = true)
    void updateDeliveryMethod(@MappingTarget DeliveryMethod deliveryMethod,RequestDeliveryMethod requestDeliveryMethod);
}
