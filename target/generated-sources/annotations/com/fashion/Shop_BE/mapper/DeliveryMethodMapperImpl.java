package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestDeliveryMethod;
import com.fashion.Shop_BE.dto.response.DeliveryMethodResponse;
import com.fashion.Shop_BE.entity.DeliveryMethod;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T17:14:17+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class DeliveryMethodMapperImpl implements DeliveryMethodMapper {

    @Override
    public DeliveryMethod toDeliveryMethod(RequestDeliveryMethod deliveryMethod) {
        if ( deliveryMethod == null ) {
            return null;
        }

        DeliveryMethod.DeliveryMethodBuilder deliveryMethod1 = DeliveryMethod.builder();

        deliveryMethod1.deliveryMethodName( deliveryMethod.getDeliveryMethodName() );
        deliveryMethod1.deliveryFee( deliveryMethod.getDeliveryFee() );
        deliveryMethod1.deliveryMethodDescription( deliveryMethod.getDeliveryMethodDescription() );

        return deliveryMethod1.build();
    }

    @Override
    public DeliveryMethodResponse toDeliveryMethodResponse(DeliveryMethod deliveryMethod) {
        if ( deliveryMethod == null ) {
            return null;
        }

        DeliveryMethodResponse.DeliveryMethodResponseBuilder deliveryMethodResponse = DeliveryMethodResponse.builder();

        deliveryMethodResponse.deliveryMethodId( deliveryMethod.getDeliveryMethodId() );
        deliveryMethodResponse.deliveryMethodName( deliveryMethod.getDeliveryMethodName() );
        if ( deliveryMethod.getDeliveryFee() != null ) {
            deliveryMethodResponse.deliveryFee( String.valueOf( deliveryMethod.getDeliveryFee() ) );
        }
        deliveryMethodResponse.deliveryMethodLogo( deliveryMethod.getDeliveryMethodLogo() );
        deliveryMethodResponse.deliveryMethodDescription( deliveryMethod.getDeliveryMethodDescription() );

        return deliveryMethodResponse.build();
    }

    @Override
    public void updateDeliveryMethod(DeliveryMethod deliveryMethod, RequestDeliveryMethod requestDeliveryMethod) {
        if ( requestDeliveryMethod == null ) {
            return;
        }

        deliveryMethod.setDeliveryMethodName( requestDeliveryMethod.getDeliveryMethodName() );
        deliveryMethod.setDeliveryFee( requestDeliveryMethod.getDeliveryFee() );
        deliveryMethod.setDeliveryMethodDescription( requestDeliveryMethod.getDeliveryMethodDescription() );
    }
}
