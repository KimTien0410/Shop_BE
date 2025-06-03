package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.entity.Voucher;
import com.fashion.Shop_BE.entity.VoucherUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherUserRepository extends JpaRepository<VoucherUser, Integer> {
    List<VoucherUser> findByUserAndVoucher(User user, Voucher voucher);
    boolean existsByUser_UserIdAndVoucher_VoucherIdAndIsUsedTrue(Long userId, Long voucherId);
}
