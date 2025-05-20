package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.*;
import com.fashion.Shop_BE.dto.response.*;
import com.fashion.Shop_BE.entity.*;
import com.fashion.Shop_BE.enums.OrderStatus;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.ProductMapper;
import com.fashion.Shop_BE.repository.*;
import com.fashion.Shop_BE.service.*;
import com.fashion.Shop_BE.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final ProductVariantRepository productVariantRepository;
    private final ProductResourceRepository productResourceRepository;
    private final SlugService slugService;
    private final OrderDetailRepository orderDetailRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    @Override
    public ProductResponse addProduct(RequestProduct requestProduct, List<RequestProductVariant> requestProductVariants, List<MultipartFile> images) {
        // Xử lý Product
        Product product = prepareProduct(requestProduct);

        // Xử lý ProductVariant
        Optional.ofNullable(requestProductVariants)
                .filter(variants -> !variants.isEmpty())
                .ifPresent(variants -> processAddProductVariants(product, variants));

        // Xử lý ProductResource
        Optional.ofNullable(images)
                .filter(imgs -> !imgs.isEmpty())
                .ifPresent(imgs -> processAddProductResources(product, imgs));

        // Cập nhật giá và số lượng sản phẩm
        updateBasePriceAndQuantity(product);
        // Lưu Product
        productRepository.save(product);

        return productMapper.productToProductResponse(product);
    }

    private Product prepareProduct(RequestProduct requestProduct) {
        Product product = productMapper.requestToProduct(requestProduct);
        product.setProductSlug(slugService.generateSlug(requestProduct.getProductName()));

        Category category = categoryRepository.findById(requestProduct.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        product.setCategory(category);

        Brand brand = brandRepository.findById(requestProduct.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        product.setBrand(brand);

        return product;
    }
    private void processAddProductVariants(Product product, List<RequestProductVariant> requestProductVariants) {
        Set<ProductVariant> productVariants = productMapper.requestToProductVariantSet(requestProductVariants);
        productVariants.forEach(variant -> variant.setProduct(product)); // Ensure product is set
        product.setProductVariants(productVariants);
    }
//    private void processAddProductVariants(Product product, List<RequestProductVariant> requestProductVariants) {
////        List<ProductVariant> productVariants = productMapper.requestToProductVariantList(requestProductVariants);
//        Set<ProductVariant> productVariants = productMapper.requestToProductVariantSet(requestProductVariants);
//        productVariants.forEach(variant -> variant.setProduct(product));
//        product.setProductVariants(productVariants);
//    }
    private void updateBasePriceAndQuantity(Product product) {
        Double minPrice = product.getProductVariants().stream()
                .map(ProductVariant::getVariantPrice)
                .min(Double::compare)
                .orElse(0.0);

        int totalQuantity = product.getProductVariants().stream()
                .mapToInt(ProductVariant::getStockQuantity)
                .sum();

        product.setBasePrice(minPrice);
        product.setProductQuantity(totalQuantity);
    }

    private void processAddProductResources(Product product, List<MultipartFile> images) {
        Set<ProductResource> productResources = new LinkedHashSet<>();

        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);
            Map<String, String> uploadResult = cloudinaryService.uploadImageProduct(image);

            ProductResource resource = ProductResource.builder()
                    .resourceName(uploadResult.get("resource_name"))
                    .resourceType(uploadResult.get("resource_type"))
                    .resourceUrl(uploadResult.get("resource_url"))
                    .isPrimary(i == 0) // ✅ ảnh đầu tiên là isPrimary = true
                    .product(product)
                    .build();

            productResources.add(resource);
        }

        product.setProductResources(productResources);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(Long productId, RequestUpdateProduct requestProduct, List<RequestUpdateProductVariant> requestProductVariants, List<MultipartFile> images) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if(!product.getProductName().equals(requestProduct.getProductName())){
            product.setProductSlug(slugService.generateSlug(requestProduct.getProductName()));
        }
        productMapper.updateProduct(product, requestProduct);
//        product = productRepository.save(product);
        product = productRepository.saveAndFlush(product);
        Category category=categoryRepository.findById(requestProduct.getCategoryId()).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if(!product.getCategory().equals(category)){
            product.setCategory(category);
        }
        Brand brand=brandRepository.findById(requestProduct.getBrandId()).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_FOUND));
        if(!product.getBrand().equals(brand)){
            product.setBrand(brand);
        }

        // Cập nhật ProductVariant nếu có thay đổi
        if(requestProductVariants!=null && !requestProductVariants.isEmpty()){
            updateProductVariants(product, requestProductVariants);
        }
        // Cập nhật ProductResource nếu có thay đổi
        if(images!=null && !images.isEmpty()){
            updateProductResources(product,images);
        }
        // Cập nhật giá và số lượng sản phẩm
        updateBasePriceAndQuantity(product);
        productRepository.save(product);
        return productMapper.productToProductResponse(product);
    }
    private void updateProductVariants(Product product, List<RequestUpdateProductVariant> requestProductVariants) {
        Set<ProductVariant> existingVariants = product.getProductVariants();
        if (existingVariants == null) {
            existingVariants = new HashSet<>();
            product.setProductVariants(existingVariants); // gán nếu ban đầu null
        }

        Map<Long, ProductVariant> existingById = existingVariants.stream()
                .filter(v -> v.getVariantId() != null)
                .collect(Collectors.toMap(ProductVariant::getVariantId, Function.identity()));

        Set<ProductVariant> updatedVariants = new HashSet<>();

        for (RequestUpdateProductVariant request : requestProductVariants) {
            String reqColor = request.getColor().trim().toLowerCase();
            String reqSize = request.getSize().toString().toLowerCase();

            if (request.getVariantId() != null && existingById.containsKey(request.getVariantId())) {
                ProductVariant variant = existingById.get(request.getVariantId());
                productMapper.updateProductVariant(variant, request);
                System.out.println("Product ID before assigning to variant: " + product.getProductId());

                variant.setProduct(product);
                updatedVariants.add(variant);
            } else {
                Optional<ProductVariant> duplicate = Stream.concat(existingVariants.stream(), updatedVariants.stream())
                        .filter(v -> v.getColor().trim().equalsIgnoreCase(reqColor)
                                && v.getSize().toString().equalsIgnoreCase(reqSize))
                        .findFirst();

                if (duplicate.isPresent()) {
                    ProductVariant existing = duplicate.get();
                    if (!Objects.equals(existing.getStockQuantity(), request.getStockQuantity())) {
                        existing.setStockQuantity(request.getStockQuantity());
                    }
                    if (!Objects.equals(existing.getVariantPrice(), request.getVariantPrice())) {
                        existing.setVariantPrice(request.getVariantPrice());
                    }
                    updatedVariants.add(existing);
                } else {
                    ProductVariant newVariant = productMapper.requestUpdateToProductVariant(request);
                    System.out.println("Product ID before assigning to variant: " + product.getProductId());

                    newVariant.setProduct(product);
                    updatedVariants.add(newVariant);
                }
            }
        }

        // Xóa variant cũ không còn trong danh sách cập nhật
        existingVariants.removeIf(v -> !updatedVariants.contains(v));

        // Thêm variant mới vào danh sách
        existingVariants.addAll(updatedVariants);

        // 🔥 Nếu cần: gọi product.setProductVariants(existingVariants); đảm bảo đúng liên kết
        product.setProductVariants(existingVariants);
    }

    private void updateProductResources(Product product, List<MultipartFile> images) {
        // 1. Lấy danh sách các resource hiện tại
        Set<ProductResource> existingResources = product.getProductResources();
        Set<String> existingUrls = existingResources.stream()
                .map(ProductResource::getResourceUrl)
                .collect(Collectors.toSet());

        // 2. Upload ảnh mới và lưu kết quả
        List<Map<String, String>> uploadedResults = images.stream()
                .map(cloudinaryService::uploadImageProduct)
                .toList(); // dùng List để giữ thứ tự

        // 3. Tạo danh sách URL từ ảnh mới
        Set<String> newUrls = uploadedResults.stream()
                .map(result -> result.get("resource_url"))
                .collect(Collectors.toSet());

        // 4. Thêm ảnh mới (và set isPrimary cho ảnh đầu tiên)
        for (int i = 0; i < uploadedResults.size(); i++) {
            Map<String, String> result = uploadedResults.get(i);
            String resourceUrl = result.get("resource_url");

            if (!existingUrls.contains(resourceUrl)) {
                ProductResource newResource = ProductResource.builder()
                        .resourceName(result.get("resource_name"))
                        .resourceType(result.get("resource_type"))
                        .resourceUrl(resourceUrl)
                        .isPrimary(i == 0) //  Chỉ ảnh đầu tiên là isPrimary
                        .product(product)
                        .build();

                product.getProductResources().add(newResource);
            }
        }

        // 5. Xoá ảnh cũ không còn trong danh sách ảnh mới
        existingResources.removeIf(resource -> !newUrls.contains(resource.getResourceUrl()));

        // 6. Nếu không có ảnh nào được gán primary (ví dụ tất cả đều đã có sẵn),
        //     đảm bảo vẫn có ít nhất 1 ảnh primary
        boolean hasPrimary = product.getProductResources().stream().anyMatch(ProductResource::isPrimary);
        if (!hasPrimary && !product.getProductResources().isEmpty()) {
            product.getProductResources().iterator().next().setPrimary(true);
        }
    }


    @Override
    public ProductResponse getProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        List<ProductResource> a = product.getProductResources().stream().filter(
                productResource ->
            productResource.isPrimary()).toList();
        a.get(0);
        return productMapper.productToProductResponse(product);
    }

    @Override
    public ProductResponse getProductBySlug(String productSlug) {
        Product product=productRepository.findByProductSlug(productSlug).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        return productMapper.productToProductResponse(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)
        );
//        // Kiểm tra xem sản phẩm có tồn tại trong đơn hàng nào không
//        List<ProductVariant> productVariants =productVariantRepository.findByProduct(product);
//        if(!productVariants.isEmpty()){
//            for(ProductVariant productVariant: productVariants){
//                //Lấy danh sách trạng thái đơn hàng của sản phẩm
//                List<OrderStatus> orderStatuses=orderDetailRepository.findOrderStatusesByProductVariantId(productVariant.getVariantId());
//                //Kiểm tra đơn hàng nếu có đơn hàng đang xử lý, đang giao.. thì không cho xóa
//                boolean hasActiveOrders= orderStatuses.stream()
//                        .anyMatch(status -> status!= OrderStatus.DELIVERED && status!= OrderStatus.CANCELLED);
//                if(hasActiveOrders){
//                    throw new AppException(ErrorCode.PRODUCT_VARIANT_IN_ORDER);
//                }
//            }
            // Xoá mềm sản phẩm
            product.setDeleted(true);
            product.setDeletedAt(new Date());
            productRepository.save(product);
//        }

    }

    @Transactional
    @Override
    public void restoreProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setDeletedAt(null);
        product.setDeleted(false);
        productRepository.save(product);
    }




    @Override
    public ProductItemResponse getProductByProductVariant(Long productVariantId) {
       return productVariantRepository.findProductItemResponseByVariantId(productVariantId);
    }

    @Override
    public String getThumbnailImage(Product product) {
        return product.getProductResources().stream()
                .filter(ProductResource::isPrimary)
                .map(ProductResource::getResourceUrl)
                .findFirst()
                .orElse(null);
    }


    @Transactional
    @Override
    public Page<ProductItemResponse> filterProduct(ProductFilterRequest productFilterRequest,int page,int size,String sortBy,String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, getSort(sortBy,sortOrder));
        Specification<Product> specification= ProductSpecification.filterBy(productFilterRequest);
        Page<Product> products = productRepository.findAll(specification, pageable);
        return convertToProductItemResponse(products);

    }

    private Sort getSort(String sortBy, String sortOrder) {
        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return switch (sortBy) {
            case "price" -> Sort.by(direction, "basePrice");
            case "bestseller" -> Sort.by(direction, "productSold");
            case "createdAt", "newest" -> Sort.by(direction, "createdAt");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }

    @Transactional
    @Override
    public Page<ProductItemResponse> getAllProductsByCategoryId(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;
        // Lấy tất cả danh mục con và chính danh mục cha
        List<Category> categories = categoryRepository.findAllByParentCategoryId(categoryId);
        List<Long> categoryIds = categories.stream().map(Category::getCategoryId).toList();
        // Tìm tất cả sản phẩm thuộc các danh mục này
        products = productRepository.findAllByCategoryIds(categoryIds, pageable);
        return convertToProductItemResponse(products);
    }
    @Override
    public Page<ProductItemResponse> getAllProductsByNewArrival(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByIsNewArrivalAndDeletedAtIsNull(true,pageable);
        return convertToProductItemResponse(products);
    }

    @Override
    public Page<ProductItemResponse> getAllProductsByBestSeller(int page, int size) {
        Page<Product> bestSellers = productRepository.findAllBestSellers(PageRequest.of(page, size));
        return convertToProductItemResponse(bestSellers);
    }

    public Page<ProductItemResponse> convertToProductItemResponse(Page<Product> products) {
        return products.map(product -> {
            String imageUrl = product.getProductResources().stream()
                    .filter(ProductResource::isPrimary)
                    .map(ProductResource::getResourceUrl)
                    .findFirst()
                    .orElse(null);

            return new ProductItemResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductSlug(),
                    product.getProductSold(),
                    product.getBasePrice(),
                    imageUrl,
                    product.getCategory().getCategoryId(),
                    product.getBrand().getBrandId()
            );
        });
    }

    @Override
    public Page<ProductResponse> getAllProductsByAdmin(String search, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products;
        if (search == null || search.trim().isEmpty()) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findAllBySearch(search, pageable);
        }
        return products.map(productMapper::productToProductResponse);

    }
}
