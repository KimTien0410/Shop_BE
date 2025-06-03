package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestBrand;
import com.fashion.Shop_BE.dto.response.BrandResponse;
import com.fashion.Shop_BE.entity.Brand;
import com.fashion.Shop_BE.enums.EntityStatus;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.BrandMapper;
import com.fashion.Shop_BE.repository.BrandRepository;
import com.fashion.Shop_BE.service.BrandService;
import com.fashion.Shop_BE.service.CloudinaryService;
import com.fashion.Shop_BE.service.SlugService;
import com.fashion.Shop_BE.specification.BrandSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final CloudinaryService cloudinaryService;
    private final SlugService slugService;
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public BrandResponse addBrand(RequestBrand request) {
        Brand brand=brandMapper.toBrand(request);
        brand.setBrandCode(slugService.generateSlug(request.getBrandName()));
        MultipartFile brandThumbnail=request.getBrandThumbnail();

        if(brandThumbnail!=null && !brandThumbnail.isEmpty()){
            String url = cloudinaryService.uploadFile(brandThumbnail);
            brand.setBrandThumbnail(url);
        }
        brandRepository.save(brand);
        return brandMapper.toBrandResponse(brand);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public BrandResponse updateBrand(Long brandId,RequestBrand request) {
        Brand brand=brandRepository.findById(brandId)
                .orElseThrow(()->new AppException(ErrorCode.BRAND_NOT_FOUND));
        brandMapper.updateBrand(brand,request);
        brand.setBrandCode(slugService.generateSlug(request.getBrandName()));
        MultipartFile file=request.getBrandThumbnail();
        if(file!=null && !file.isEmpty()){
            if(brand.getBrandThumbnail()!=null){
                cloudinaryService.deleteOldFile(brand.getBrandThumbnail());
            }
            String url = cloudinaryService.uploadFile(file);
            brand.setBrandThumbnail(url);
        }
        brandRepository.save(brand);
        return brandMapper.toBrandResponse(brand);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public void deleteBrand(Long brandId) {
        Brand brand=brandRepository.findById(brandId)
                .orElseThrow(()->new AppException(ErrorCode.BRAND_NOT_FOUND));
        brand.setDeletedAt(new Date());
        brandRepository.save(brand);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public void restoreBrand(Long brandId) {
        Brand brand=brandRepository.findById(brandId)
                .orElseThrow(()->new AppException(ErrorCode.BRAND_NOT_FOUND));
        if(brand.getDeletedAt()==null){
            throw new AppException(ErrorCode.BRAND_NOT_DELETED);
        }
        brand.setDeletedAt(null);
    }

    @Override
    public Page<BrandResponse> getBrandByFilter(String search, EntityStatus status, int page, int size, String sortBy, String sortDirection) {
        Specification<Brand> spec = BrandSpecification.withFilters(search, status);
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return brandRepository.findAll(spec, pageable).map(brandMapper::toBrandResponse);

    }


    @Override
    public BrandResponse getBrand(Long brandId) {
        Brand brand=brandRepository.findById(brandId)
                .orElseThrow(()->new AppException(ErrorCode.BRAND_NOT_FOUND));
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public Page<BrandResponse> getAllBrandActive(String search, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Brand> brands;
        if (search == null || search.trim().isEmpty()) {
            brands = brandRepository.findAll(pageable);
        } else {
            brands = brandRepository.searchBrands(search, pageable);
        }
        return brands.map(brandMapper::toBrandResponse);
    }

    @Override
    public BrandResponse getByBrandCode(String brandCode) {
        Brand brand=brandRepository.findByBrandCode(brandCode)
                .orElseThrow(()->new AppException(ErrorCode.BRAND_NOT_FOUND));
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public BrandResponse getByBrandName(String brandName) {
        Brand brand=brandRepository.findByBrandName(brandName)
                .orElseThrow(()->new AppException(ErrorCode.BRAND_NOT_FOUND));
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public Page<BrandResponse> getAllBrandByAdmin(String search, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Brand> brands;
        if (search == null || search.trim().isEmpty()) {
            brands = brandRepository.findAll(pageable);
        } else {
            brands = brandRepository.findAllBrands(search,pageable);
        }
        return brands.map(brandMapper::toBrandResponse);

    }



}
