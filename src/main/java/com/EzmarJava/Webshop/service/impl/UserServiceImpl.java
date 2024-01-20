package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.dto.RegistrationDTO;
import com.EzmarJava.Webshop.dto.checkout.CheckoutUserDataDTO;
import com.EzmarJava.Webshop.model.Role;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.repository.RoleRepository;
import com.EzmarJava.Webshop.repository.UserRepository;
import com.EzmarJava.Webshop.service.CartService;
import com.EzmarJava.Webshop.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService
{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private CartService cartService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository1, PasswordEncoder passwordEncoder1, CartService cartService)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository1;
        this.passwordEncoder = passwordEncoder1;
        this.cartService = cartService;
    }

    @Override
    public void createUser(RegistrationDTO registrationDTO)
    {
        // Create user object
        User user = new User();

        // Set fields
        user.setUsername(registrationDTO.getUsername());
        user.setFirstname(registrationDTO.getFirstname());
        user.setLastname(registrationDTO.getLastname());
        user.setEmail(registrationDTO.getEmail());
        user.setAddress(registrationDTO.getAddress());

        // Encode password
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        // Add role
        Role role = roleRepository.findByAuthority("ROLE_USER");
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);
        user.setAuthorities(authorities);

        // save user
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    public void updateUser(CheckoutUserDataDTO checkoutUserDataDTO, Long userId) {
        User user = userRepository.findById(userId).get();

        user.setFirstname(checkoutUserDataDTO.getFirstname());
        user.setLastname(checkoutUserDataDTO.getLastname());
        user.setEmail(checkoutUserDataDTO.getEmail());
        user.setUsername(checkoutUserDataDTO.getUsername());
        user.setAddress(checkoutUserDataDTO.getAddress());

        userRepository.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).get();
    }
}
