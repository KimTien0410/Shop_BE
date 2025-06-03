package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestCart;
import com.fashion.Shop_BE.dto.request.RequestProductVariantId;
import com.fashion.Shop_BE.dto.response.CartDetailResponse;
import com.fashion.Shop_BE.dto.response.CartResponse;
import com.fashion.Shop_BE.entity.Cart;
import com.fashion.Shop_BE.entity.CartDetail;
import com.fashion.Shop_BE.entity.ProductVariant;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.repository.CartDetailRepository;
import com.fashion.Shop_BE.repository.CartRepository;
import com.fashion.Shop_BE.repository.ProductVariantRepository;
import com.fashion.Shop_BE.repository.UserRepository;
import com.fashion.Shop_BE.service.CartService;
import com.fashion.Shop_BE.service.ProductService;
import com.fashion.Shop_BE.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final UserService userService;

    private CartResponse toCartResponse(Cart cart) {
        List<CartDetailResponse> cartDetailResponses = cart.getCartDetails().stream().map(
                cartDetail -> {
                    ProductVariant productVariant= cartDetail.getProductVariant();
                    return  CartDetailResponse.builder()
                            .cartDetailId(cartDetail.getCartDetailId())
                            .productVariantId(productVariant.getVariantId())
                            .productName(productVariant.getProduct().getProductName())
                            .productImage(productService.getThumbnailImage(productVariant.getProduct()))
                            .size(productVariant.getSize())
                            .color(productVariant.getColor())
                            .price(productVariant.getVariantPrice())
                            .quantity(cartDetail.getQuantity())
                            .subTotal(cartDetail.getQuantity() * productVariant.getVariantPrice())
                            .build();
                }
        ).toList();
        double totalPrice = cartDetailResponses.stream().mapToDouble(CartDetailResponse::getSubTotal).sum();
        int totalQuantity =cartDetailResponses.stream().mapToInt(CartDetailResponse::getQuantity).sum();

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUser().getUserId())
                .totalPrice(totalPrice)
                .totalQuantity(totalQuantity)
                .cartDetailResponses(cartDetailResponses)
                .build();
    }


    @Override
    public CartResponse getCartByUser() {
        User user = userService.getUserAuthenticated();

        Cart cart=cartRepository.findByUser(user).orElseThrow(
                ()->new AppException(ErrorCode.CART_NOT_FOUND));

        return toCartResponse(cart);
    }

    @Transactional
    @Override
    public CartResponse addToCart(RequestCart requestAddCart) {
        // Kiểm tra user đã đăng nhập chua
        User user = userService.getUserAuthenticated();
        //1 Kiem tra xem sản phẩm có tồn tại không.
        ProductVariant productVariant=productVariantRepository.findById(requestAddCart.getProductVariantId())
                .orElseThrow(()-> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND_IN_CART));

        int quantityToAdd=requestAddCart.getQuantity();
        int stockQuantity=productVariant.getStockQuantity();


        // Tìm giỏ hàng của người dùng
        Optional<Cart> optionalCart=cartRepository.findByUser(user);

        Cart cart;
        if(optionalCart.isPresent()){
            cart=optionalCart.get();
        }
        else{
            cart=new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        // Kiểm tra xem sản phẩm đã tồn tại trong gio hang chưa

        Optional<CartDetail> optionalCartDetail=cartDetailRepository
                .findByCartAndProductVariant(cart,productVariant);
        int currentQuantityInCart=0;
        if(optionalCartDetail.isPresent()){
            currentQuantityInCart=optionalCartDetail.get().getQuantity();
        }
        // Tính tổng số lượng sản phẩm trong giỏ hàng
        int totalQuantity=currentQuantityInCart+quantityToAdd;
        if(totalQuantity>stockQuantity){
            throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_ENOUGH_QUANTITY);
        }

        CartDetail cartDetail;
        if(optionalCartDetail.isPresent()){
            // Nếu sản phẩm đã co, cập nhật so lượng
            cartDetail=optionalCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity()+requestAddCart.getQuantity());
            cartDetail = cartDetailRepository.save(cartDetail);
        }
        else{
            // Nêu san pham chua co, tạo mới CartItem
            cartDetail = CartDetail.builder()
                    .cart(cart)
                    .quantity(requestAddCart.getQuantity())
                    .price(productVariant.getVariantPrice())
                    .productVariant(productVariant)
                    .build();
            cartDetailRepository.save(cartDetail);
        }

        cart.getCartDetails().add(cartDetail);
        cartRepository.save(cart);

        return toCartResponse(cart);
    }

    @Transactional
    public CartResponse removeMultipleProductsFromCart(RequestProductVariantId productVariantIds) {
        // Kiểm tra user đã đăng nhập chưa
        User user = userService.getUserAuthenticated();

        // Tìm giỏ hàng của người dùng
        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new AppException(ErrorCode.CART_NOT_FOUND));

        // Duyệt qua danh sách productVariantIds
        for (Long productVariantId : productVariantIds.getProductVariantIds()) {
            // 1. Kiểm tra xem sản phẩm có tồn tại không.
            ProductVariant productVariant = productVariantRepository.findById(productVariantId)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

            // Tìm CartDetail
            Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByCartAndProductVariant(cart, productVariant);

            if (optionalCartDetail.isPresent()) {
                CartDetail cartDetail = optionalCartDetail.get();
                // Xóa sản phẩm khỏi giỏ hàng
                cart.getCartDetails().remove(cartDetail);
                cartDetailRepository.delete(cartDetail);
            } else {
                // Nếu sản phẩm không tồn tại trong giỏ hàng, thông báo lỗi
                throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND_IN_CART);
            }
        }
        cartRepository.save(cart);
        return toCartResponse(cart);
    }

    @Override
    public CartResponse updateCartDetail(RequestCart requestUpdateCart) {
        // Kiểm tra user đã đăng nhập chưa
        User user = userService.getUserAuthenticated();

        // 1. Kiểm tra xem sản phẩm có tồn tại không.
        ProductVariant productVariant = productVariantRepository.findById(requestUpdateCart.getProductVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        int newQuantity = requestUpdateCart.getQuantity();
        int stockQuantity = productVariant.getStockQuantity();

        // Tìm giỏ hàng của người dùng
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        });

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByCartAndProductVariant(cart, productVariant);

        if (optionalCartDetail.isPresent()) {
            CartDetail cartDetail = optionalCartDetail.get();

            if (newQuantity <= 0) {
                // Nếu số lượng giảm xuống 0, xóa sản phẩm khỏi giỏ hàng
                cart.getCartDetails().remove(cartDetail);
                cartRepository.save(cart);
                cartDetailRepository.delete(cartDetail);
            } else if (newQuantity <= stockQuantity) {
                // Nếu số lượng hợp lệ, cập nhật số lượng
                cartDetail.setQuantity(newQuantity);
                cartDetailRepository.save(cartDetail);
            } else {
                // Nếu số lượng vượt quá số lượng tồn kho, thông báo lỗi
                throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_ENOUGH_QUANTITY);
            }
        } else {
            // Nếu sản phẩm không tồn tại trong giỏ hàng, thông báo lỗi
            throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND_IN_CART);
        }

        return toCartResponse(cart);


    }
}
