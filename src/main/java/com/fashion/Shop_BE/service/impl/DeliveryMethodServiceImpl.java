package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestDeliveryMethod;
import com.fashion.Shop_BE.dto.response.DeliveryMethodResponse;
import com.fashion.Shop_BE.entity.DeliveryMethod;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.DeliveryMethodMapper;
import com.fashion.Shop_BE.repository.DeliveryMethodRepository;
import com.fashion.Shop_BE.service.DeliveryMethodService;
import com.fashion.Shop_BE.service.CloudinaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class DeliveryMethodServiceImpl implements DeliveryMethodService {
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final DeliveryMethodMapper deliveryMethodMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<DeliveryMethodResponse> getAll() {
        List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findByDeliveryIsActiveTrue();
        return deliveryMethods.stream().map(deliveryMethodMapper::toDeliveryMethodResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryMethodResponse getById(Long id) {
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));

        return deliveryMethodMapper.toDeliveryMethodResponse(deliveryMethod);
    }

    @Override
    @Transactional
    public DeliveryMethodResponse add(RequestDeliveryMethod requestDeliveryMethod) {
        DeliveryMethod deliveryMethod = deliveryMethodMapper.toDeliveryMethod(requestDeliveryMethod);
        MultipartFile logo=requestDeliveryMethod.getDeliveryMethodLogo();
        if(logo!=null && !logo.isEmpty()){
            String url = cloudinaryService.uploadFile(logo);
            deliveryMethod.setDeliveryMethodLogo(url);
        }
        deliveryMethodRepository.save(deliveryMethod);
        return deliveryMethodMapper.toDeliveryMethodResponse(deliveryMethod);
    }

    @Override
    @Transactional
    public DeliveryMethodResponse update(Long id,RequestDeliveryMethod requestDeliveryMethod) {
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));
        deliveryMethodMapper.updateDeliveryMethod(deliveryMethod, requestDeliveryMethod);
        MultipartFile logo=requestDeliveryMethod.getDeliveryMethodLogo();
        if(logo!=null && !logo.isEmpty()){
            if(deliveryMethod.getDeliveryMethodLogo()!=null && !logo.isEmpty()){
                cloudinaryService.deleteOldFile(deliveryMethod.getDeliveryMethodLogo());
            }
            String url = cloudinaryService.uploadFile(logo);
            deliveryMethod.setDeliveryMethodLogo(url);
        }
        deliveryMethodRepository.save(deliveryMethod);

        return deliveryMethodMapper.toDeliveryMethodResponse(deliveryMethod);
    }

    @Override
    public List<DeliveryMethodResponse> getAllByAdmin() {
        List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findAll();
        return deliveryMethods.stream().map(deliveryMethodMapper::toDeliveryMethodResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        DeliveryMethod deliveryMethod=deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));
        if (deliveryMethod.getOrders() != null && !deliveryMethod.getOrders().isEmpty()) {
            throw new AppException(ErrorCode.DELIVERY_METHOD_HAS_ACTIVE_ORDERS);
        }
        if(deliveryMethod.getDeliveryMethodLogo()!=null){
            cloudinaryService.deleteOldFile(deliveryMethod.getDeliveryMethodLogo());
        }
        deliveryMethodRepository.delete(deliveryMethod);
    }

    @Override
    @Transactional
    public void changeStatusMethod(Long id) {
        DeliveryMethod deliveryMethod=deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));
        deliveryMethod.setDeliveryIsActive(!deliveryMethod.isDeliveryIsActive());
        deliveryMethodRepository.save(deliveryMethod);
    }
}
