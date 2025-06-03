package com.fashion.Shop_BE.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Boolean gender;
    private String userPhone;
    private String userAvatar;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    private Set<RoleResponse> roles;
}
