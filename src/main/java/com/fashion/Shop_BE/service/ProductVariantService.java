package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestProductVariant;
import com.fashion.Shop_BE.dto.request.RequestUpdateProductVariant;
import com.fashion.Shop_BE.dto.response.ProductVariantResponse;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductVariant;

import java.util.List;
import java.util.Set;

public interface ProductVariantService {
    public ProductVariantResponse getById(Long id);
    public List<ProductVariantResponse> getByProductId(Long productId);
    public ProductVariantResponse getByProductIdAndColorAndSize(Long productId, String color, String size);
    public Set<String> getColorsByProductId(Long productId);
    public Set<String> getSizeByProductId(Long productId);

    public List<ProductVariant> saveProductVariants(Product product, List<RequestProductVariant> productVariants);
    public void updateProductVariant(Product product, List<RequestUpdateProductVariant> updatedVariants);


}
