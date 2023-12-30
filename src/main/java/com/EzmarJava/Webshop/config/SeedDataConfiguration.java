package com.EzmarJava.Webshop.config;

import com.EzmarJava.Webshop.model.Cart;
import com.EzmarJava.Webshop.model.Role;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.repository.RoleRepository;
import com.EzmarJava.Webshop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SeedDataConfiguration
{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public SeedDataConfiguration(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Seed DB with roles and an admin user
    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository)
    {
        return args ->
        {
            // If there is already an admin role, return
            if(roleRepository.findByAuthority("ROLE_ADMIN") != null) return;

            // Create and save roles
            Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_USER"));

            // Add authorities for the admin user
            Set<Role> authorities = new HashSet<>();
            authorities.add(adminRole);

            // Create admin user
            User admin = new User("admin", "admin@example.com", passwordEncoder.encode("password"), authorities);

            userRepository.save(admin);
        };
    }
}
