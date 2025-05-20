package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestReceiverAddress;
import com.fashion.Shop_BE.dto.response.ReceiverAddressResponse;
import com.fashion.Shop_BE.entity.ReceiverAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReceiverAddressMapper {

    ReceiverAddress toReceiverAddress(RequestReceiverAddress requestReceiverAddress);
    @Mapping(source="default", target="isDefault")
    ReceiverAddressResponse toReceiverAddressResponse(ReceiverAddress receiverAddress);

    void updateReceiverAddress(@MappingTarget ReceiverAddress receiverAddress, RequestReceiverAddress requestReceiverAddress);


}
