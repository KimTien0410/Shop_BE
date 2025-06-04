package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestReceiverAddress;
import com.fashion.Shop_BE.dto.response.ReceiverAddressResponse;
import com.fashion.Shop_BE.entity.ReceiverAddress;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-29T20:44:09+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ReceiverAddressMapperImpl implements ReceiverAddressMapper {

    @Override
    public ReceiverAddress toReceiverAddress(RequestReceiverAddress requestReceiverAddress) {
        if ( requestReceiverAddress == null ) {
            return null;
        }

        ReceiverAddress.ReceiverAddressBuilder receiverAddress = ReceiverAddress.builder();

        receiverAddress.receiverName( requestReceiverAddress.getReceiverName() );
        receiverAddress.receiverPhone( requestReceiverAddress.getReceiverPhone() );
        receiverAddress.street( requestReceiverAddress.getStreet() );
        receiverAddress.ward( requestReceiverAddress.getWard() );
        receiverAddress.district( requestReceiverAddress.getDistrict() );
        receiverAddress.city( requestReceiverAddress.getCity() );

        return receiverAddress.build();
    }

    @Override
    public ReceiverAddressResponse toReceiverAddressResponse(ReceiverAddress receiverAddress) {
        if ( receiverAddress == null ) {
            return null;
        }

        ReceiverAddressResponse.ReceiverAddressResponseBuilder receiverAddressResponse = ReceiverAddressResponse.builder();

        receiverAddressResponse.isDefault( receiverAddress.isDefault() );
        receiverAddressResponse.addressId( receiverAddress.getAddressId() );
        receiverAddressResponse.receiverName( receiverAddress.getReceiverName() );
        receiverAddressResponse.receiverPhone( receiverAddress.getReceiverPhone() );
        receiverAddressResponse.street( receiverAddress.getStreet() );
        receiverAddressResponse.ward( receiverAddress.getWard() );
        receiverAddressResponse.district( receiverAddress.getDistrict() );
        receiverAddressResponse.city( receiverAddress.getCity() );

        return receiverAddressResponse.build();
    }

    @Override
    public void updateReceiverAddress(ReceiverAddress receiverAddress, RequestReceiverAddress requestReceiverAddress) {
        if ( requestReceiverAddress == null ) {
            return;
        }

        receiverAddress.setReceiverName( requestReceiverAddress.getReceiverName() );
        receiverAddress.setReceiverPhone( requestReceiverAddress.getReceiverPhone() );
        receiverAddress.setStreet( requestReceiverAddress.getStreet() );
        receiverAddress.setWard( requestReceiverAddress.getWard() );
        receiverAddress.setDistrict( requestReceiverAddress.getDistrict() );
        receiverAddress.setCity( requestReceiverAddress.getCity() );
    }
}
