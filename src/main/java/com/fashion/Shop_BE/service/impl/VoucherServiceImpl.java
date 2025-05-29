package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestVoucher;
import com.fashion.Shop_BE.dto.response.VoucherResponse;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.entity.Voucher;
import com.fashion.Shop_BE.entity.VoucherUser;
import com.fashion.Shop_BE.enums.DiscountType;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.VoucherMapper;
import com.fashion.Shop_BE.repository.UserRepository;
import com.fashion.Shop_BE.repository.VoucherRepository;
import com.fashion.Shop_BE.repository.VoucherUserRepository;
import com.fashion.Shop_BE.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class

VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherUserRepository voucherUserRepository;
    private final VoucherMapper voucherMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public VoucherResponse addVoucher(RequestVoucher requestVoucher) {
        Voucher voucher = voucherMapper.toVoucher(requestVoucher);
        voucher.setCreatedAt(new Date());
        voucherRepository.save(voucher);
        return voucherMapper.toVoucherResponse(voucher);
    }

    @Override
    public VoucherResponse getVoucherById(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.VOUCHER_NOT_FOUND));

        return voucherMapper.toVoucherResponse(voucher);
    }

    @Override
    public VoucherResponse getVoucherByCode(String voucherCode) {
        Voucher voucher=voucherRepository.findByVoucherCode(voucherCode)
                .orElseThrow(()-> new AppException(ErrorCode.VOUCHER_NOT_FOUND));
        return voucherMapper.toVoucherResponse(voucher);
    }

    @Override
    public List<VoucherResponse> getAllVouchers() {
        List<Voucher> vouchers=voucherRepository.findAll();
        return vouchers.stream().map(voucherMapper::toVoucherResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public VoucherResponse updateVoucher(Long id, RequestVoucher requestVoucher) {
        Voucher voucher=voucherRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.VOUCHER_NOT_FOUND));
        voucherMapper.updateVoucher(voucher, requestVoucher);
        voucherRepository.save(voucher);

        return voucherMapper.toVoucherResponse(voucher);
    }

    @Transactional
    @Override
    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }

    @Transactional
    @Override
    public double applyVoucher(Long userId, String voucherCode, Double orderTotal) {
        Voucher voucher =voucherRepository.findByVoucherCode(voucherCode)
                .orElseThrow(()-> new AppException(ErrorCode.VOUCHER_NOT_FOUND));
//        Voucher voucher = voucherRepository.findByVoucherCode(voucherCode)
//                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại"));

        // Kiểm tra thời gian
        Date now = new Date();
        if (now.before(voucher.getStartDate()) || now.after(voucher.getEndDate())) {
            throw new RuntimeException("Voucher đã hết hạn hoặc chưa bắt đầu");
        }

        // Kiểm tra đơn hàng tối thiểu
        if (voucher.getMinOrderValue() != null && orderTotal < voucher.getMinOrderValue()) {
            throw new RuntimeException("Đơn hàng chưa đạt giá trị tối thiểu để áp dụng voucher");
        }

        // Kiểm tra số lượng còn lại
        if (voucher.getQuantity() <= 0) {
            throw new RuntimeException("Voucher đã hết lượt sử dụng");
        }

        // Kiểm tra user đã dùng voucher chưa
        boolean alreadyUsed = voucherUserRepository.existsByUser_UserIdAndVoucher_VoucherIdAndIsUsedTrue(userId, voucher.getVoucherId());
        if (alreadyUsed) {
            throw new RuntimeException("Bạn đã sử dụng voucher này rồi");
        }

        // Tính giá trị giảm
        double discount = 0;
        if (voucher.getDiscountType() == DiscountType.PERCENTAGE) {
            discount = orderTotal * voucher.getDiscountValue() / 100;
            if (voucher.getMaxOrderValue() != null && discount > voucher.getMaxOrderValue()) {
                discount = voucher.getMaxOrderValue();
            }
        } else {
            discount = voucher.getDiscountValue();
        }

        // Lưu vào voucher_user (đánh dấu là đã sử dụng)
        VoucherUser voucherUser = new VoucherUser();
        voucherUser.setVoucher(voucher);
        voucherUser.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User không tồn tại")));
        voucherUser.setUsed(true);
        voucherUser.setUsedAt(now);
        voucherUserRepository.save(voucherUser);

        // Giảm số lượng voucher còn lại
        voucher.setQuantity(voucher.getQuantity() - 1);
        voucherRepository.save(voucher);

        return discount;
    }
    @Override
    public List<VoucherResponse> getActiveVouchers() {
        Date now = new Date();
        System.out.println("Current Date: "+now);
        // Lọc các voucher đang hoạt động
        System.out.println("Active Vouchers: "+
                voucherRepository.findAll().stream()
                        .filter(v ->
                                now.after(v.getStartDate()) &&
                                now.before(v.getEndDate()) &&
                                v.getQuantity() > 0)
                        .map(voucherMapper::toVoucherResponse)
                        .collect(Collectors.toList()));
       return voucherRepository.findAll().stream()
                .filter(v ->
                        now.after(v.getStartDate()) &&
                        now.before(v.getEndDate()) &&
                        v.getQuantity() > 0)
                .map(voucherMapper::toVoucherResponse)
                .collect(Collectors.toList());
    }
}
