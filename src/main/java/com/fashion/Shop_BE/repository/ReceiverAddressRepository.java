package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.ReceiverAddress;
import com.fashion.Shop_BE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiverAddressRepository extends JpaRepository<ReceiverAddress, Long> {
    @Query("SELECT r FROM ReceiverAddress r WHERE r.user = :user AND r.deletedAt IS NULL")
    List<ReceiverAddress> findByUserAndDeletedAtIsNull(User user);
    @Query("SELECT r FROM ReceiverAddress r WHERE r.user = :user AND r.addressId = :addressId AND r.deletedAt IS NULL")
    Optional<ReceiverAddress> findByUserAndReceiverId(User user, Long addressId);
    @Query("SELECT r FROM ReceiverAddress r WHERE r.user = :user AND r.isDefault = true AND r.deletedAt IS NULL")
    Optional<ReceiverAddress> findDefaultAddressByUser(User user);
    @Query("SELECT r FROM ReceiverAddress r WHERE r.user = :user AND r.deletedAt IS NULL AND r.isDefault = true")
    Optional<ReceiverAddress> findByUserAndAddressDefault(User user);

}
