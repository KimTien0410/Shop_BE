package com.fashion.Shop_BE.dto.request;

import com.fashion.Shop_BE.validation.ValidFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProductResource {
    @ValidFile(message="FILE_INVALID")
    private MultipartFile productImage;
    private boolean isPrimary;
}
