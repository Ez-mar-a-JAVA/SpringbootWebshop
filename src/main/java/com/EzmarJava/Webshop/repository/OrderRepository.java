package com.EzmarJava.Webshop.repository;

import com.EzmarJava.Webshop.model.Order;
import com.EzmarJava.Webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order getById(Long id);

    Order findByUser(User user);

    List<Order> findOrdersByUser(User user);


}
