package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.cartItem.AddCartItemDTO;
import com.EzmarJava.Webshop.model.User;

public interface CartService {
    void addToCart(AddCartItemDTO cartItemDTO, Long userId);
    int getCartQuantity(User user);
}
