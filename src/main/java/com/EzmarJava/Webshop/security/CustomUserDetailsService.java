package com.EzmarJava.Webshop.security;

import com.EzmarJava.Webshop.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        // keep var bcs of name conflict...
        var user = userRepository.findByUsername(username);
        if(user != null)
        {
            User authenticatedUser =
                    new User(
                            user.getUsername(),
                            user.getPassword(),
                            user.getAuthorities().stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList())
                    );
            return authenticatedUser;
        }else
        {
            throw new UsernameNotFoundException("Invalid credentials!");
        }
    }
}
