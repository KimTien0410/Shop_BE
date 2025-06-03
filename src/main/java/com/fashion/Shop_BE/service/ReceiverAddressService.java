package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestReceiverAddress;
import com.fashion.Shop_BE.dto.response.ReceiverAddressResponse;

import java.util.List;

public interface ReceiverAddressService {
    public ReceiverAddressResponse add(RequestReceiverAddress requestReceiverAddress);
    public List<ReceiverAddressResponse> getAll();
    public ReceiverAddressResponse getById(Long receiverId);
    public ReceiverAddressResponse update(Long receiverId, RequestReceiverAddress requestReceiverAddress);
    public void delete(Long receiverId);
    public void setDefault(Long receiverId);
    public ReceiverAddressResponse getDefault();

}
