package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.cart.CartDTO;
import com.EzmarJava.Webshop.exception.CartException;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("cartTotal", cartService.getCartTotal(user));

        // Check for the flash attribute and add it to the model
        if (model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", model.asMap().get("errorMessage"));
        }

        return "cart/cart";
    }

    @PostMapping("/cart/delete-cart-item/{cartItemId}")
    public String deleteCartItem(@PathVariable Long cartItemId, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        User user = ((User) authentication.getPrincipal());

        try {
            cartService.deleteCartItem(cartItemId, user);
        }catch (CartException cartException) {
            redirectAttributes.addFlashAttribute("errorMessage", cartException.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/decrease-cart-item/{cartItemId}")
    public String decreaseCartItemQuantity(@PathVariable Long cartItemId, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        User user = ((User) authentication.getPrincipal());

        try {
            cartService.decreaseCartItem(cartItemId, user);
        }catch (CartException cartException) {
            redirectAttributes.addFlashAttribute("errorMessage", cartException.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/increase-cart-item/{cartItemId}")
    public String increaseCartItemQuantity(@PathVariable Long cartItemId, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        User user = ((User) authentication.getPrincipal());

        try {
            cartService.increaseCartItem(cartItemId, user);
        }catch (CartException cartException) {
            redirectAttributes.addFlashAttribute("errorMessage", cartException.getMessage());
        }

        return "redirect:/cart";
    }

}
