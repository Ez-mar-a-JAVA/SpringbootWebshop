package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.RegistrationDTO;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.service.CartService;
import com.EzmarJava.Webshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController
{
    private final UserService userService;

    public AuthenticationController(UserService userService)
    {
        this.userService = userService;

    }

    @GetMapping("/login")
    public String loginPage(Authentication authentication)
    {
        if(authentication != null && authentication.isAuthenticated())
        {
            return "/";
        }

        return "auth/login";
    }

    @GetMapping("/register")
    public String registrationForm(Model model){
        // this object holds form data
        RegistrationDTO user = new RegistrationDTO();
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") RegistrationDTO user, BindingResult bindingResult, Model model)
    {
        User existingUser = userService.findByUsername(user.getUsername());
        if(existingUser != null)
        {
            bindingResult.rejectValue("username", null , "Username is taken.");
        }

        if(bindingResult.hasErrors())
        {
            model.addAttribute("user", user);
            return "auth/register";
        }

        userService.createUser(user);

        return "redirect:/login";
    }


    // TEST ROUTE WILL BE REMOVED LATER
    @GetMapping("/")
    public String testGuest()
    {
        return "home";
    }

    @GetMapping("/admin")
    public String admin()
    {
        return "admin/admin";
    }
}
