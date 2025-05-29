package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.*;
import com.fashion.Shop_BE.dto.response.ProductItemResponse;
import com.fashion.Shop_BE.dto.response.ProductResponse;
import com.fashion.Shop_BE.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public ProductResponse addProduct(RequestProduct requestProduct, List<RequestProductVariant> productVariants, List<MultipartFile> images);
    public ProductResponse updateProduct(Long productId, RequestUpdateProduct requestProduct, List<RequestUpdateProductVariant> productVariants, List<MultipartFile> images);
    public ProductResponse getProduct(Long productId);
    public ProductResponse getProductBySlug(String productSlug);
    public void deleteProduct(Long productId);
    public void restoreProduct(Long productId);


//    public Page<ProductItemResponse> getAllProducts(int page, int size);

    Page<ProductItemResponse> filterProduct(ProductFilterRequest productFilterRequest,int page,int size,String sortBy,String sortOrder);

    public ProductItemResponse getProductByProductVariant(Long productVariantId);

    public String getThumbnailImage(Product product);

    public Page<ProductItemResponse> getAllProductsByCategoryId(Long categoryId, int page, int size);
    public Page<ProductItemResponse> getAllProductsByNewArrival(int page, int size);
    public Page<ProductItemResponse> getAllProductsByBestSeller(int page, int size);


    public Page<ProductResponse> getAllProductsByAdmin(String search,int page, int size, String sortBy, String sortDirection);

    public Product getProductById(Long productId);
}
