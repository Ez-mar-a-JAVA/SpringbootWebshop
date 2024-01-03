package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.dto.cartItem.AddCartItemDTO;
import com.EzmarJava.Webshop.exception.CartException;
import com.EzmarJava.Webshop.model.Cart;
import com.EzmarJava.Webshop.model.CartItem;
import com.EzmarJava.Webshop.model.Product;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.repository.CartRepository;
import com.EzmarJava.Webshop.repository.ProductRepository;
import com.EzmarJava.Webshop.repository.UserRepository;
import com.EzmarJava.Webshop.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository, ModelMapper modelMapper, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public void addToCart(AddCartItemDTO cartItemDTO, Long userId) {

        // Get product
        Product product = productRepository.getById(cartItemDTO.getProductId());

        // Check if quantity exceeds available quantity
        if(!(product.getQuantity() < cartItemDTO.getQuantity())) {
            // Get current user
            User user = userRepository.findById(userId).get();

            // Check if cart has been created
            if(user.getCart() == null) {
                Cart cart = new Cart();
                cart.setQuantity(0);
                cart.setUser(user);

                cartRepository.save(cart);

                user.setCart(cart);

                userRepository.save(user);
            }



            // Check if product exists
            // If so update quantity, do not create a new CartItem, use existing one
            CartItem cartItem = user.getCart().getCartItem().stream()
                    .filter(item -> item.getProduct().getId().equals(product.getId()))
                    .findFirst()
                    .orElse(new CartItem());

            cartItem.setProduct(product);
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            cartItem.setCart(user.getCart());

            user.getCart().getCartItem().add(cartItem);

            // Update carts overall quantity
            int totalQuantity = user.getCart().getCartItem().stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();
            user.getCart().setQuantity(totalQuantity);
            userRepository.save(user);

            // If cart is updated than update product quantity
            product.setQuantity(product.getQuantity() - cartItemDTO.getQuantity());
            productRepository.save(product);

        }else {
            throw new CartException("Product quantity exceeds available quantity!");
        }


    }

    @Override
    public int getCartQuantity(User user) {
        Cart cart = cartRepository.getCartByUser(user);
        return cart.getQuantity();
    }
}
