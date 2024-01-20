package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.exception.OrderException;
import com.EzmarJava.Webshop.model.Order;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/my-orders")
    public String myOrdersPage(Authentication authentication, Model model) {
        User user = ((User) authentication.getPrincipal());
        model.addAttribute("orders", orderService.getAllOrdersByUser(user));

        return "order/my_orders";
    }

    @GetMapping("/my-orders/order/{orderId}")
    public String viewOrderPage(@PathVariable Long orderId, Authentication authentication, Model model) {
        User user = ((User) authentication.getPrincipal());

        try {
            Order order = orderService.getOrderById(orderId, user);

            model.addAttribute("order", order);
        }catch (OrderException orderException) {
            return "redirect:/my-orders";
        }

        return "order/view_order";
    }

    @GetMapping("/admin/orders")
    public String viewAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/all_orders";
    }


    @GetMapping("/admin/view-order/{orderId}")
    public String viewOrderAdmin(@PathVariable Long orderId, Authentication authentication, Model model) {
        User user = ((User) authentication.getPrincipal());

        try {
            Order order = orderService.getOrderById(orderId, user);

            model.addAttribute("order", order);
        }catch (OrderException orderException) {
            return "redirect:/my-orders";
        }

        return "order/view_order";
    }

    @GetMapping("/admin/update-order-status/{orderId}")
    public String updateOrderStatusPage(@PathVariable Long orderId, Authentication authentication, Model model) {
        User user = ((User) authentication.getPrincipal());
        model.addAttribute("order", orderService.getOrderById(orderId, user));
        return "order/update_order_status";
    }

    @PostMapping("/admin/update-order-status/{orderId}")
    public String updateOrderStatus(@PathVariable Long orderId, @ModelAttribute Order order) {
        orderService.updateOrderStatus(orderId, order.getStatus());
        return "redirect:/admin/orders";
    }
}
