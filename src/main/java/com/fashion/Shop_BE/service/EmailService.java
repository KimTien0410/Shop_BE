package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.entity.User;

public interface EmailService {
    public void sendEmailToVerifyEmail(User user);
    public void sendEmail(String recipientEmail, String subject, String body);
}
