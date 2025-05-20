package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestCart;
import com.fashion.Shop_BE.dto.request.RequestProductVariantId;
import com.fashion.Shop_BE.dto.response.CartResponse;

public interface CartService {
    public CartResponse getCartByUser();
    public CartResponse addToCart(RequestCart requestAddCart);
    public CartResponse removeMultipleProductsFromCart(RequestProductVariantId productVariantIds);
    public CartResponse updateCartDetail(RequestCart requestAddCart);

}
