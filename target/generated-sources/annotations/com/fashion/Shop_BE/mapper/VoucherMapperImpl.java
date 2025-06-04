package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestVoucher;
import com.fashion.Shop_BE.dto.response.VoucherResponse;
import com.fashion.Shop_BE.entity.Voucher;
import com.fashion.Shop_BE.enums.DiscountType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T17:14:17+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class VoucherMapperImpl implements VoucherMapper {

    @Override
    public Voucher toVoucher(RequestVoucher requestVoucher) {
        if ( requestVoucher == null ) {
            return null;
        }

        Voucher.VoucherBuilder voucher = Voucher.builder();

        voucher.voucherCode( requestVoucher.getVoucherCode() );
        if ( requestVoucher.getDiscountType() != null ) {
            voucher.discountType( Enum.valueOf( DiscountType.class, requestVoucher.getDiscountType() ) );
        }
        voucher.discountValue( requestVoucher.getDiscountValue() );
        voucher.minOrderValue( requestVoucher.getMinOrderValue() );
        voucher.maxOrderValue( requestVoucher.getMaxOrderValue() );
        voucher.startDate( requestVoucher.getStartDate() );
        voucher.endDate( requestVoucher.getEndDate() );
        voucher.quantity( requestVoucher.getQuantity() );

        return voucher.build();
    }

    @Override
    public VoucherResponse toVoucherResponse(Voucher voucher) {
        if ( voucher == null ) {
            return null;
        }

        VoucherResponse.VoucherResponseBuilder voucherResponse = VoucherResponse.builder();

        voucherResponse.voucherId( voucher.getVoucherId() );
        voucherResponse.voucherCode( voucher.getVoucherCode() );
        if ( voucher.getDiscountType() != null ) {
            voucherResponse.discountType( voucher.getDiscountType().name() );
        }
        voucherResponse.discountValue( voucher.getDiscountValue() );
        voucherResponse.minOrderValue( voucher.getMinOrderValue() );
        voucherResponse.maxOrderValue( voucher.getMaxOrderValue() );
        voucherResponse.quantity( voucher.getQuantity() );
        voucherResponse.startDate( voucher.getStartDate() );
        voucherResponse.endDate( voucher.getEndDate() );

        return voucherResponse.build();
    }

    @Override
    public void updateVoucher(Voucher voucher, RequestVoucher requestVoucher) {
        if ( requestVoucher == null ) {
            return;
        }

        voucher.setVoucherCode( requestVoucher.getVoucherCode() );
        if ( requestVoucher.getDiscountType() != null ) {
            voucher.setDiscountType( Enum.valueOf( DiscountType.class, requestVoucher.getDiscountType() ) );
        }
        else {
            voucher.setDiscountType( null );
        }
        voucher.setDiscountValue( requestVoucher.getDiscountValue() );
        voucher.setMinOrderValue( requestVoucher.getMinOrderValue() );
        voucher.setMaxOrderValue( requestVoucher.getMaxOrderValue() );
        voucher.setStartDate( requestVoucher.getStartDate() );
        voucher.setEndDate( requestVoucher.getEndDate() );
        voucher.setQuantity( requestVoucher.getQuantity() );
    }
}
