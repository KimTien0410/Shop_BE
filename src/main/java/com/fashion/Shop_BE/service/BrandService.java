package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestBrand;
import com.fashion.Shop_BE.dto.response.BrandResponse;
import com.fashion.Shop_BE.enums.EntityStatus;
import org.springframework.data.domain.Page;

public interface BrandService {
    public BrandResponse addBrand(RequestBrand request);
    public BrandResponse updateBrand(Long brandId,RequestBrand request);
    public void deleteBrand(Long brandId);
    public BrandResponse getBrand(Long brandId);
    public Page<BrandResponse> getAllBrandActive(String search, int page, int size, String sortBy, String sortDirection);

    public BrandResponse getByBrandCode(String brandCode);
    public BrandResponse getByBrandName(String brandName);

    public Page<BrandResponse > getAllBrandByAdmin(String search, int page, int size, String sortBy, String sortDirection);
    public void restoreBrand(Long brandId);


    public Page<BrandResponse > getBrandByFilter(String search, EntityStatus status, int page, int size, String sortBy, String sortDirection);


}
