package com.fashion.Shop_BE.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // File ko được rỗng
        }

        // Kiểm tra kích thước file (ví dụ: tối đa 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            return false;
        }

        // Kiểm tra định dạng file (chỉ chấp nhận ảnh PNG, JPG)
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/png") || contentType.equals("image/jpeg"));
    }
}
