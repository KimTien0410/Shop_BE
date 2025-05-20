package com.fashion.Shop_BE.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestAuthentication {
    @Email(message = "EMAIL_INVALID")
    private String email;
    private String password;
}
