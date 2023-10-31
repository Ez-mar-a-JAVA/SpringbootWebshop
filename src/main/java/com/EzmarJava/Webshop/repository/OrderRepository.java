package com.EzmarJava.Webshop.repository;

import com.EzmarJava.Webshop.model.Order;
import com.EzmarJava.Webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order getById(Long id);

    Order findByUser(User user);


}
