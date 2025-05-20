package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Value("$spring.mail.username")
    private String sender;
    @Override
    public void sendEmailToVerifyEmail(User user) {
        String subject = "Xác thực tài khoản";

        // Đảm bảo URL chính xác (localhost thay vì locahost)
        String verifyLink = "http://localhost:8080/api/auth/verify?token=" + user.getVerificationCode();

        // Nội dung email với HTML
        String body = "<p>Nhấp vào link sau để xác thực tài khoản:</p>" +
                "<p><a href='" + verifyLink + "' style='background-color:#4CAF50; color:white; padding:10px 20px; text-align:center; text-decoration:none; display:inline-block; font-size:16px; border-radius:5px;'>Xác thực ngay</a></p>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(body, true); // ✅ Quan trọng: Bật HTML mode

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.SEND_VERIFY_EMAIL_FAIL);
        }
    }
}
