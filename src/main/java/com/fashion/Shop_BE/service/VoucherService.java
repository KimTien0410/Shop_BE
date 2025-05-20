package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestVoucher;
import com.fashion.Shop_BE.dto.response.VoucherResponse;
import com.fashion.Shop_BE.entity.User;

import java.util.List;

public interface VoucherService {
    public VoucherResponse addVoucher(RequestVoucher requestVoucher);
    public VoucherResponse getVoucherById(Long id);
    public VoucherResponse getVoucherByCode(String voucherCode);
    public List<VoucherResponse> getAllVouchers();
    public VoucherResponse updateVoucher(Long id, RequestVoucher requestVoucher);
    public void deleteVoucher(Long id);
    public double applyVoucher(Long userId, String voucherCode,Double orderValue);
    public List<VoucherResponse> getActiveVouchers();
}
