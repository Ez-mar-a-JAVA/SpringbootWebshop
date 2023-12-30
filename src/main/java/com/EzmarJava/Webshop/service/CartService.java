package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.cartItem.AddCartItemDTO;

public interface CartService {
    void addToCart(AddCartItemDTO cartItemDTO, Long userId);
}
