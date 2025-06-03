package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestDeliveryMethod;
import com.fashion.Shop_BE.dto.response.DeliveryMethodResponse;

import java.util.List;

public interface DeliveryMethodService {
    public List<DeliveryMethodResponse> getAll();
    public DeliveryMethodResponse getById(Long id);
    public DeliveryMethodResponse add(RequestDeliveryMethod requestDeliveryMethod);
    public DeliveryMethodResponse update(Long id,RequestDeliveryMethod requestDeliveryMethod);

    public List<DeliveryMethodResponse> getAllByAdmin();
    public void delete(Long id);
    public void changeStatusMethod(Long id);
}
