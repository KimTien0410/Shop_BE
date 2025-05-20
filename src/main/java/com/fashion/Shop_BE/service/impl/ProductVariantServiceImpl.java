package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestProductVariant;
import com.fashion.Shop_BE.dto.request.RequestUpdateProductVariant;
import com.fashion.Shop_BE.dto.response.ProductResponse;
import com.fashion.Shop_BE.dto.response.ProductVariantResponse;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductVariant;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.ProductMapper;
import com.fashion.Shop_BE.repository.ProductRepository;
import com.fashion.Shop_BE.repository.ProductVariantRepository;
import com.fashion.Shop_BE.service.ProductVariantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public ProductVariantResponse getById(Long id) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        return productMapper.variantToProductVariantResponse(productVariant);
    }

    @Override
    public List<ProductVariantResponse> getByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        List<ProductVariant> productVariants = productVariantRepository.findByProduct(product);
        return productVariants.stream()
                .map(productVariant->productMapper.variantToProductVariantResponse(productVariant))
                .collect(Collectors.toList());
    }

    @Override
    public ProductVariantResponse getByProductIdAndColorAndSize(Long productId, String color, String size) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductVariant productVariant =productVariantRepository.findByProductAndColorAndSize(productId,color,size);
        return productMapper.variantToProductVariantResponse(productVariant);
    }

    @Override
    public Set<String> getColorsByProductId(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productVariantRepository.findAllColorsByProduct(productId);
    }

    @Override
    public Set<String> getSizeByProductId(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productVariantRepository.findAllSizesByProduct(productId);
    }

    @Transactional
    @Override
    public List<ProductVariant> saveProductVariants(Product product, List<RequestProductVariant> productVariants) {
        List<ProductVariant> variants = productMapper.requestToProductVariantList(productVariants);
        return productVariantRepository.saveAll(variants);
    }

    @Override
    public void updateProductVariant(Product product, List<RequestUpdateProductVariant> updatedVariants) {
        List<ProductVariant> existingVariants = productVariantRepository.findByProduct(product);

        // So sánh danh sách cũ và mới để cập nhật
        Map<Long, ProductVariant> variantMap = existingVariants.stream()
                .collect(Collectors.toMap(ProductVariant::getVariantId, variant -> variant));

        for (RequestUpdateProductVariant variantDTO : updatedVariants) {
            if (variantDTO.getVariantId() == null || !variantMap.containsKey(variantDTO.getVariantId())) {
                // Nếu ID không tồn tại, thêm mới
                ProductVariant newVariant = new ProductVariant();
                newVariant=productMapper.requestUpdateToProductVariant(variantDTO);
                newVariant.setProduct(product);

                productVariantRepository.save(newVariant);
            } else {
                // Nếu đã tồn tại, kiểm tra thay đổi và cập nhật
                ProductVariant existingVariant = variantMap.get(variantDTO.getVariantId());
                productMapper.updateProductVariant(existingVariant, variantDTO);
                productVariantRepository.save(existingVariant);
                variantMap.remove(variantDTO.getVariantId()); // Đánh dấu đã xử lý
            }
        }

        // Xóa các biến thể không có trong danh sách cập nhật
        if (!variantMap.isEmpty()) {
            productVariantRepository.deleteAll(variantMap.values());
        }
    }
}
