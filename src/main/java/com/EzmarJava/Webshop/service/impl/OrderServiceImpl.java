package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.model.*;
import com.EzmarJava.Webshop.repository.CartRepository;
import com.EzmarJava.Webshop.repository.OrderItemRepository;
import com.EzmarJava.Webshop.repository.OrderRepository;
import com.EzmarJava.Webshop.repository.UserRepository;
import com.EzmarJava.Webshop.service.CartService;
import com.EzmarJava.Webshop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository, CartService cartService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void createOrder(Long userId) {
        User user = userRepository.findById(userId).get();
        Cart cart = cartRepository.getCartByUser(user);

        // Create order and map fields
        Order order = new Order();
        order.setOrderItems(new ArrayList<>());
        order.setTotal(cartService.getCartTotal(user));
        order.setUser(user);
        order.setStatus("Payed");


        // Get list of order items
        List<CartItem> cartItems = cart.getCartItem();

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());

            orderItems.add(orderItem);
            order.getOrderItems().add(orderItem);
           // orderItemRepository.save(orderItem);
        }


        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        return orderRepository.findOrdersByUser(user);
    }
}
