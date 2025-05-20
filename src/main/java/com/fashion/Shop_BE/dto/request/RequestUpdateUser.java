package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.validation.PhoneNumber;
import com.fashion.Shop_BE.validation.ValidFile;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.fashion.Shop_BE.exception.ErrorCode.DATE_OF_BIRTH_THAN_PAST;

@Getter
@Builder
public class RequestUpdateUser {
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "DATE_OF_BIRTH_THAN_PAST")
    private LocalDate dateOfBirth;
    private boolean gender=false;
    @PhoneNumber(message="PHONE_INVALID")
    private String userPhone;
    @ValidFile(message="FILE_INVALID")
    private MultipartFile avatar;
}
