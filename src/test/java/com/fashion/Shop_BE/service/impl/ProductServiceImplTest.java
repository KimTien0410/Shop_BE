package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestProduct;
import com.fashion.Shop_BE.dto.request.RequestProductVariant;
import com.fashion.Shop_BE.dto.request.RequestUpdateProduct;
import com.fashion.Shop_BE.dto.request.RequestUpdateProductVariant;
import com.fashion.Shop_BE.dto.response.ProductResponse;
import com.fashion.Shop_BE.entity.Brand;
import com.fashion.Shop_BE.entity.Category;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.ProductVariant;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.ProductMapper;
import com.fashion.Shop_BE.repository.BrandRepository;
import com.fashion.Shop_BE.repository.CategoryRepository;
import com.fashion.Shop_BE.repository.ProductRepository;
import com.fashion.Shop_BE.service.CloudinaryService;
import com.fashion.Shop_BE.service.SlugService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private SlugService slugService;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private ProductServiceImpl productService;

    private RequestProduct requestProduct;
    private RequestUpdateProduct requestUpdateProduct;

    @BeforeEach
    void setUp() {
        requestProduct = new RequestProduct();
        requestProduct.setProductName("Test Product");
        requestProduct.setCategoryId(1L);
        requestProduct.setBrandId(1L);

        requestUpdateProduct = new RequestUpdateProduct();
        requestUpdateProduct.setProductName("Updated Product");
        requestUpdateProduct.setCategoryId(1L);
        requestUpdateProduct.setBrandId(1L);
    }

    @Test
    void addProduct_ShouldAddProduct_WhenValidRequest() {
        // Arrange
        Product product = new Product();
        product.setProductVariants(new HashSet<>()); // Initialize productVariants

        Category category = new Category();
        Brand brand = new Brand();

        when(productMapper.requestToProduct(requestProduct)).thenReturn(product);
        when(slugService.generateSlug(requestProduct.getProductName())).thenReturn("test-product-slug");
        when(categoryRepository.findById(requestProduct.getCategoryId())).thenReturn(Optional.of(category));
        when(brandRepository.findById(requestProduct.getBrandId())).thenReturn(Optional.of(brand));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.productToProductResponse(product)).thenReturn(new ProductResponse());

        // Act
        ProductResponse response = productService.addProduct(requestProduct, Collections.emptyList(), Collections.emptyList());

        // Assert
        assertNotNull(response);
        verify(productMapper, times(1)).requestToProduct(requestProduct);
        verify(slugService, times(1)).generateSlug(requestProduct.getProductName());
        verify(categoryRepository, times(1)).findById(requestProduct.getCategoryId());
        verify(brandRepository, times(1)).findById(requestProduct.getBrandId());
        verify(productRepository, times(1)).save(product);
    }

//    @Test
//    void addProduct_ShouldThrowException_WhenCategoryNotFound() {
//        // Arrange
//        Product product = new Product(); // Initialize product
//        when(productMapper.requestToProduct(requestProduct)).thenReturn(product);
//        when(categoryRepository.findById(requestProduct.getCategoryId())).thenReturn(Optional.empty());
//
//        // Act & Assert
//        AppException exception = assertThrows(AppException.class, () -> productService.addProduct(requestProduct, Collections.emptyList(), Collections.emptyList()));
//        assertEquals(ErrorCode.CATEGORY_NOT_FOUND, exception.getErrorCode());
//        verify(categoryRepository, times(1)).findById(requestProduct.getCategoryId());
//        verifyNoInteractions(brandRepository, productMapper, productRepository);
//    }
//@Test
//void updateProduct_ShouldUpdateProduct_WhenValidRequest() {
//    // Arrange
//    Product product = new Product();
//    product.setProductName("Existing Product");
//    product.setCategory(new Category()); // Initialize category
//    product.setBrand(new Brand()); // Initialize brand
//
//    Category category = new Category();
//    Brand brand = new Brand();
//
//    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//    when(slugService.generateSlug(requestUpdateProduct.getProductName())).thenReturn("updated-product-slug");
//    when(categoryRepository.findById(requestUpdateProduct.getCategoryId())).thenReturn(Optional.of(category));
//    when(brandRepository.findById(requestUpdateProduct.getBrandId())).thenReturn(Optional.of(brand));
//    when(productMapper.productToProductResponse(product)).thenReturn(new ProductResponse());
//
//    // Act
//    ProductResponse response = productService.updateProduct(1L, requestUpdateProduct, Collections.emptyList(), Collections.emptyList());
//
//    // Assert
//    assertNotNull(response);
//    verify(productRepository, times(1)).findById(1L);
//    verify(slugService, times(1)).generateSlug(requestUpdateProduct.getProductName());
//    verify(categoryRepository, times(1)).findById(requestUpdateProduct.getCategoryId());
//    verify(brandRepository, times(1)).findById(requestUpdateProduct.getBrandId());
//    verify(productRepository, times(1)).save(product);
//}
    @Test
    void updateProduct_ShouldThrowException_WhenProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> productService.updateProduct(1L, requestUpdateProduct, Collections.emptyList(), Collections.emptyList()));
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
        verify(productRepository, times(1)).findById(1L);
        verifyNoInteractions(slugService, categoryRepository, brandRepository, productMapper);
    }
}