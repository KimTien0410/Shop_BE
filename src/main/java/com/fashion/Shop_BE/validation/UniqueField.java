package com.fashion.Shop_BE.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
    String message() default "Giá trị đã tồn tại";
    Class<?> entity(); // Entity cần kiểm tra
    String fieldName(); // Tên trường cần kiểm tra
    String idField() ; // Trường ID (mặc định là "id")
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}