package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestPaymentMethod;
import com.fashion.Shop_BE.dto.response.PaymentMethodResponse;
import com.fashion.Shop_BE.entity.PaymentMethod;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.PaymentMethodMapper;
import com.fashion.Shop_BE.repository.PaymentMethodRepository;
import com.fashion.Shop_BE.service.PaymentMethodService;
import com.fashion.Shop_BE.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<PaymentMethodResponse> getAll() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByPaymentIsActiveTrue();
        return paymentMethods.stream().map(paymentMethodMapper::toPaymentMethodResponse).collect(Collectors.toList());
    }

    @Override
    public PaymentMethodResponse getById(Long id) {
        PaymentMethod paymentMethod=paymentMethodRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

        return paymentMethodMapper.toPaymentMethodResponse(paymentMethod);
    }

    @Transactional
    @Override
    public PaymentMethodResponse save(RequestPaymentMethod requestPaymentMethod) {
        PaymentMethod paymentMethod=paymentMethodMapper.toPaymentMethod(requestPaymentMethod);
        MultipartFile logo=requestPaymentMethod.getPaymentMethodLogo();
        if(logo != null && !logo.isEmpty()){
            String url=cloudinaryService.uploadFile(logo);
            paymentMethod.setPaymentMethodLogo(url);
        }
        paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.toPaymentMethodResponse(paymentMethod);
    }

    @Override
    @Transactional
    public PaymentMethodResponse update(Long id, RequestPaymentMethod requestPaymentMethod) {
        PaymentMethod existingPaymentMethod=paymentMethodRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));
        paymentMethodMapper.updatePaymentMethod(existingPaymentMethod, requestPaymentMethod);
        MultipartFile logo=requestPaymentMethod.getPaymentMethodLogo();
        if(logo != null && !logo.isEmpty()){
            if(existingPaymentMethod.getPaymentMethodLogo()!=null){
                cloudinaryService.deleteOldFile(existingPaymentMethod.getPaymentMethodLogo());
            }
            String url=cloudinaryService.uploadFile(logo);
            existingPaymentMethod.setPaymentMethodLogo(url);
        }
        paymentMethodRepository.save(existingPaymentMethod);

        return paymentMethodMapper.toPaymentMethodResponse(existingPaymentMethod);
    }


    @Override
    public List<PaymentMethodResponse> getAllByAdmin() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        return paymentMethods.stream().map(paymentMethodMapper::toPaymentMethodResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeStatusMethod(Long paymentMethodId) {
        PaymentMethod paymentMethod=paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));
        boolean status =paymentMethod.isPaymentIsActive();
        paymentMethod.setPaymentIsActive(!status);
        paymentMethodRepository.save(paymentMethod);
    }
}
