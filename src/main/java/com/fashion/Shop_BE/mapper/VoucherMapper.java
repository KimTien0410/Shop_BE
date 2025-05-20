package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestVoucher;
import com.fashion.Shop_BE.dto.response.VoucherResponse;
import com.fashion.Shop_BE.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    Voucher toVoucher(RequestVoucher requestVoucher);
    VoucherResponse toVoucherResponse(Voucher voucher);
    void updateVoucher(@MappingTarget Voucher voucher, RequestVoucher requestVoucher);
}
