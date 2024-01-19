package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.model.Order;
import com.EzmarJava.Webshop.model.User;

import java.util.List;

public interface OrderService {
    void createOrder(Long userId);
    List<Order> getAllOrdersByUser(User user);
    Order getOrderById(Long orderId, User user);
}
