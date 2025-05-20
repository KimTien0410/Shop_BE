package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.validation.UniqueField;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RequestAddUser {
    @Email(message="EMAIL_INVALID")
    @UniqueField(entity = User.class,idField = "userId", fieldName = "email", message = "EMAIL_EXISTED")
    private String email;
    @Length(min=8,message = "PASSWORD_INVALID")
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}
