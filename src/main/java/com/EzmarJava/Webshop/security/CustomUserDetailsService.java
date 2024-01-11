package com.EzmarJava.Webshop.security;

import com.EzmarJava.Webshop.model.Cart;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.repository.CartRepository;
import com.EzmarJava.Webshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CustomUserDetailsService(UserRepository userRepository, CartRepository cartRepository)
    {
        this.userRepository = userRepository;

        this.cartRepository = cartRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        if(user != null)
        {
           if(user.getCart() == null){
               // Init cart
               Cart cart = new Cart();
               cart.setQuantity(0);
               cart.setUser(user);

               cartRepository.save(cart);
               user.setCart(cart);
               userRepository.save(user);
           }
            return user;
        }else
        {
            throw new UsernameNotFoundException("Invalid username or password!");
        }
    }
}
