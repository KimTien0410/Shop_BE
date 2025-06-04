package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestPaymentMethod;
import com.fashion.Shop_BE.dto.response.PaymentMethodResponse;
import com.fashion.Shop_BE.entity.PaymentMethod;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T17:14:18+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PaymentMethodMapperImpl implements PaymentMethodMapper {

    @Override
    public PaymentMethod toPaymentMethod(RequestPaymentMethod paymentMethod) {
        if ( paymentMethod == null ) {
            return null;
        }

        PaymentMethod.PaymentMethodBuilder paymentMethod1 = PaymentMethod.builder();

        paymentMethod1.paymentMethodName( paymentMethod.getPaymentMethodName() );

        return paymentMethod1.build();
    }

    @Override
    public PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod) {
        if ( paymentMethod == null ) {
            return null;
        }

        PaymentMethodResponse.PaymentMethodResponseBuilder paymentMethodResponse = PaymentMethodResponse.builder();

        paymentMethodResponse.paymentMethodId( paymentMethod.getPaymentMethodId() );
        paymentMethodResponse.paymentMethodName( paymentMethod.getPaymentMethodName() );
        paymentMethodResponse.paymentMethodLogo( paymentMethod.getPaymentMethodLogo() );

        return paymentMethodResponse.build();
    }

    @Override
    public void updatePaymentMethod(PaymentMethod paymentMethod, RequestPaymentMethod requestPaymentMethod) {
        if ( requestPaymentMethod == null ) {
            return;
        }

        paymentMethod.setPaymentMethodName( requestPaymentMethod.getPaymentMethodName() );
    }
}
