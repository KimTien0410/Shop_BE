package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestReceiverAddress;
import com.fashion.Shop_BE.dto.response.ReceiverAddressResponse;
import com.fashion.Shop_BE.entity.ReceiverAddress;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.ReceiverAddressMapper;
import com.fashion.Shop_BE.repository.ReceiverAddressRepository;
import com.fashion.Shop_BE.service.ReceiverAddressService;
import com.fashion.Shop_BE.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ReceiverAddressServiceImpl implements ReceiverAddressService {
    private final ReceiverAddressRepository receiverAddressRepository;
    private final ReceiverAddressMapper receiverAddressMapper;
    private final UserService userService;

    @Transactional
    @Override
    public ReceiverAddressResponse add(RequestReceiverAddress requestReceiverAddress) {
        User user= userService.getUserAuthenticated();
        ReceiverAddress receiverAddress = receiverAddressMapper.toReceiverAddress(requestReceiverAddress);
        receiverAddress.setUser(user);
        receiverAddressRepository.save(receiverAddress);
        return receiverAddressMapper.toReceiverAddressResponse(receiverAddress);
    }

    @Override
    public List<ReceiverAddressResponse> getAll() {
        User user= userService.getUserAuthenticated();
        List<ReceiverAddress> receiverAddresses = receiverAddressRepository.findByUserAndDeletedAtIsNull(user);
        return receiverAddresses.stream().map(receiverAddressMapper::toReceiverAddressResponse).collect(Collectors.toList());
    }

    @Override
    public ReceiverAddressResponse getById(Long receiverId) {
        User user= userService.getUserAuthenticated();
        ReceiverAddress receiverAddress = receiverAddressRepository.findByUserAndReceiverId(user,receiverId)
                .orElseThrow(() -> new AppException(ErrorCode.RECEIVER_ADDRESS_NOT_FOUND));
        return receiverAddressMapper.toReceiverAddressResponse(receiverAddress);
    }
    @Transactional
    @Override
    public ReceiverAddressResponse update(Long receiverId, RequestReceiverAddress requestReceiverAddress) {
        User user= userService.getUserAuthenticated();
        ReceiverAddress existingReceiverAddress=receiverAddressRepository.findByUserAndReceiverId(user,receiverId)
                .orElseThrow(() -> new AppException(ErrorCode.RECEIVER_ADDRESS_NOT_FOUND));

        receiverAddressMapper.updateReceiverAddress(existingReceiverAddress,requestReceiverAddress);
        existingReceiverAddress.setUser(user);
        receiverAddressRepository.save(existingReceiverAddress);

        return receiverAddressMapper.toReceiverAddressResponse(existingReceiverAddress);
    }
    @Transactional
    @Override
    public void delete(Long receiverId) {
        User user= userService.getUserAuthenticated();
        ReceiverAddress receiverAddress = receiverAddressRepository.findByUserAndReceiverId(user,receiverId)
                .orElseThrow(() -> new AppException(ErrorCode.RECEIVER_ADDRESS_NOT_FOUND));
        receiverAddressRepository.delete(receiverAddress);
    }

    @Override
    public void setDefault(Long receiverId) {
        User user= userService.getUserAuthenticated();
        ReceiverAddress receiverAddress = receiverAddressRepository.findByUserAndReceiverId(user, receiverId)
                .orElseThrow(() -> new AppException(ErrorCode.RECEIVER_ADDRESS_NOT_FOUND));
        ReceiverAddress defaultAddress = receiverAddressRepository.findDefaultAddressByUser(user)
                .orElse(null);
        if (defaultAddress != null) {
            defaultAddress.setDefault(false);
            receiverAddressRepository.save(defaultAddress);
        }
        receiverAddress.setDefault(true);
        receiverAddressRepository.save(receiverAddress);

    }

    @Override
    public ReceiverAddressResponse getDefault() {
        User user= userService.getUserAuthenticated();
        ReceiverAddress defaultAddress = receiverAddressRepository.findDefaultAddressByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.RECEIVER_ADDRESS_NOT_FOUND));
        return receiverAddressMapper.toReceiverAddressResponse(defaultAddress);
    }


}
