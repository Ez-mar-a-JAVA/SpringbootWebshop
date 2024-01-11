package com.EzmarJava.Webshop.security;

import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;

public class CustomLogoutHandler implements LogoutHandler
{
    private final CartService cartService;

    public CustomLogoutHandler(CartService cartService)
    {
        this.cartService = cartService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());

        cartService.clearCart(user);

        try
        {
            response.sendRedirect("/");
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
