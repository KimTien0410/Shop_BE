package com.fashion.Shop_BE.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber phoneNumberNo) {
    }

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext cxt) {
        if(phoneNo == null){
            return false;
        }
        // Số điện thoại bắt đầu bằng 0 và có 10 chữ số
        if (phoneNo.matches("^0\\d{9}$")) return true;
            // Số điện thoại có dấu '-' hoặc '.' hoặc khoảng trắng
        else if (phoneNo.matches("^0\\d{2}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}$")) return true;
            // Số điện thoại với phần mở rộng (extension)
        else if (phoneNo.matches("^0\\d{2}-\\d{3}-\\d{4}\\s(x|ext)\\d{3,5}$")) return true;
            // Số điện thoại có dấu ngoặc ()
        else return phoneNo.matches("^\\(0\\d{2}\\)-\\d{3}-\\d{4}$");
    }

}
