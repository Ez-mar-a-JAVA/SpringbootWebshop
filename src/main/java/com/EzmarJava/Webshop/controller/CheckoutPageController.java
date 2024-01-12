package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.checkout.CheckoutUserDataDTO;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.service.CartService;
import com.EzmarJava.Webshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutPageController {

    private final CartService cartService;
    private final UserService userService;


    public CheckoutPageController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/checkout")
    public String checkoutPage(Authentication authentication, Model model) {
        Long userId = ((User) authentication.getPrincipal()).getId();

        User user = userService.findUserById(userId);

        CheckoutUserDataDTO checkoutDTO = new CheckoutUserDataDTO(user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), user.getAddress());

        model.addAttribute("cart", cartService.getUsersCart(user));
        model.addAttribute("cartTotal", cartService.getCartTotal(user));
        model.addAttribute("checkoutDto", checkoutDTO);

        return "checkout/checkout";
    }

    @PostMapping("/checkout/update-billing-data")
    public String updateBillingData(@ModelAttribute CheckoutUserDataDTO checkoutUserDataDTO, Authentication authentication, Model model) {
        User user = ((User) authentication.getPrincipal());
        Long userId = user.getId();
        userService.updateUser(checkoutUserDataDTO, userId);

        return "redirect:/checkout";
    }
}
