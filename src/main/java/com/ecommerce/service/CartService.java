package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;

public interface CartService {
    Cart getCartByUserId(Long userId);
    Cart addItemToCart(Long userId, Long productId, int quantity);
    Cart removeItemFromCart(Long userId, Long productId);
    void clearCart(Long userId);
} 