package com.fashion.Shop_BE.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface PayOSService {
    public String getPaymentUrl(Long orderId);
    public void cancelPaymentLink(Long orderId);
    public void verifyWebhook(ObjectNode params);
}
