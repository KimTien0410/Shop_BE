package com.fashion.Shop_BE.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    public String uploadFile(MultipartFile file);
    public Map<String,String> uploadImageProduct(MultipartFile file);
    public void deleteOldFile(String url);
}
