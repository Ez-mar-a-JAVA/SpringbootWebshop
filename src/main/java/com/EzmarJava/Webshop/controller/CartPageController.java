package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.cart.CartDTO;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartPageController {

    private final CartService cartService;

    public CartPageController(CartService cartService)
    {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cartPage(Authentication authentication, Model model) {
        User user = ((User) authentication.getPrincipal());

        CartDTO cartDTO = cartService.getUsersCart(user);
        model.addAttribute("cart", cartDTO);
        model.addAttribute("cartQuantity", cartService.getCartQuantity(user));

        return "cart/cart";
    }

}
