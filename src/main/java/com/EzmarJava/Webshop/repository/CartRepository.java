package com.EzmarJava.Webshop.repository;

import com.EzmarJava.Webshop.model.Cart;
import com.EzmarJava.Webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  CartRepository extends JpaRepository<Cart, Long> {

    Cart getById(Long id);
    Cart getCartByUser(User user);

}
