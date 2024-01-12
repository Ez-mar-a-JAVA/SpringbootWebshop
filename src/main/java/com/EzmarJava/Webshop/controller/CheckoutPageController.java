package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.Response;
import com.EzmarJava.Webshop.dto.checkout.CheckoutUserDataDTO;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.service.CartService;
import com.EzmarJava.Webshop.service.StripeService;
import com.EzmarJava.Webshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class CheckoutPageController {

    private final CartService cartService;
    private final UserService userService;
    private final StripeService stripeService;
    private final String API_PUBLIC_KEY = "pk_test_51OXVOhBvafzlZiOsp7gyzRyvT5QooQGDs4oG5lTR4s7dPxunz1UgmnqjEHVD2sWt70RcTmYhYj17a6geYhtH1f0k00Da4MQ4q3";



    public CheckoutPageController(CartService cartService, UserService userService, StripeService stripeService) {
        this.cartService = cartService;
        this.userService = userService;
        this.stripeService = stripeService;
    }

    @GetMapping("/checkout")
    public String checkoutPage(Authentication authentication, Model model) {
        Long userId = ((User) authentication.getPrincipal()).getId();

        User user = userService.findUserById(userId);

        CheckoutUserDataDTO checkoutDTO = new CheckoutUserDataDTO(user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), user.getAddress());

        model.addAttribute("cart", cartService.getUsersCart(user));
        model.addAttribute("cartTotal", cartService.getCartTotal(user));
        model.addAttribute("checkoutDto", checkoutDTO);
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);

        return "checkout/checkout";
    }

    @PostMapping("/checkout/update-billing-data")
    public String updateBillingData(@ModelAttribute CheckoutUserDataDTO checkoutUserDataDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        Long userId = user.getId();
        userService.updateUser(checkoutUserDataDTO, userId);

        return "redirect:/checkout";
    }

    /*========== REST API for Handling Payments ===================*/
    @PostMapping("/create-charge")
    public @ResponseBody
    Response createCharge(String email, String token, Authentication authentication) {

        // Get cart total and convert it to cents
        User user = ((User) authentication.getPrincipal());
        double cartTotal = cartService.getCartTotal(user);
        int convertedToCents = (int) (cartTotal * 100);

        //validate data
        if (token == null) {
            return new Response(false, "Stripe payment token is missing. Please, try again later.");
        }

        //create charge
        String chargeId = stripeService.createCharge(email, token, convertedToCents); //$9.99 USD
        if (chargeId == null) {
            return new Response(false, "An error occurred while trying to create a charge.");
        }

        // Success

        // Clear cart
        cartService.clearCart(user);

        // Create order for user

        // Redirect user to orders page -> will happen on frontend if success
        return new Response(true, "Success! Your charge id is " + chargeId);
    }
}
