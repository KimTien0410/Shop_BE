package com.fashion.Shop_BE.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "image"));
            return uploadResult.get("secure_url").toString(); // Trả về URL của ảnh
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPLOAD_FILE_FAILED);
        }
    }

    @Override
    public Map<String,String> uploadImageProduct(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());

            //  Kiểm tra nếu thiếu `public_id`
            if (!uploadResult.containsKey("public_id")) {
                throw new AppException(ErrorCode.RESOURCE_NAME_NULL);
            }

            String publicId = (String) uploadResult.get("public_id");
            String format = (String) uploadResult.get("format");
            String resourceUrl = (String) uploadResult.get("secure_url");

            Map<String, String> result = new HashMap<>();
            result.put("resource_name", publicId); // Đảm bảo tên file không null
            result.put("resource_type", format);
            result.put("resource_url", resourceUrl);

            return result;
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPLOAD_FILE_FAILED);
        }
    }

    @Override
    public void deleteOldFile(String url) {
        String publicId=extractPublicIdFromUrl(url);
        try{
            cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
        }
        catch(IOException e){
            throw new RuntimeException("Xoá ảnh thất bại: " + e.getMessage());
        }
    }

    public String extractPublicIdFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
    }
}
