package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.dto.cart.CartDTO;
import com.EzmarJava.Webshop.dto.cartItem.AddCartItemDTO;
import com.EzmarJava.Webshop.dto.cartItem.CartItemDTO;
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

import java.util.List;
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
    public void addToCart(AddCartItemDTO cartItemDTO, Long userId)
    {

        // Get product
        Product product = productRepository.getById(cartItemDTO.getProductId());

        // Check if quantity exceeds available quantity
        if (!(product.getQuantity() < cartItemDTO.getQuantity()))
        {
            // Get current user
            User user = userRepository.findById(userId).get();

            // Check if cart has been created
            if (user.getCart() == null)
            {
                Cart cart = new Cart();
                cart.setQuantity(0);
                cart.setUser(user);

                cartRepository.save(cart);
                user.setCart(cart);
                userRepository.save(user);
            }


            // Check if product exists in the cart
            CartItem cartItem = user.getCart().getCartItem().stream()
                    .filter(item -> item.getProduct().getId().equals(product.getId()))
                    .findFirst()
                    .orElse(null);

            if (cartItem == null)
            {
                // If the product is not in the cart, create a new CartItem
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(cartItemDTO.getQuantity());
                cartItem.setCart(user.getCart());
                user.getCart().getCartItem().add(cartItem);
            } else
            {
                // If the product is already in the cart, update the quantity
                cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            }

            int total = 0;
            Cart current_cart = user.getCart();
            List<CartItem> cartItemsInCart = current_cart.getCartItem();
            for (CartItem currCartItem : cartItemsInCart) {
                total += currCartItem.getQuantity();
                System.out.println("curr: "+currCartItem.getProduct().getTitle()+" quan: "+currCartItem.getQuantity());
            }
            user.getCart().setQuantity(total);
            userRepository.save(user);
            System.out.println(total);

            // If cart is updated than update product quantity
            product.setQuantity(product.getQuantity() - cartItemDTO.getQuantity());
            productRepository.save(product);

        } else
        {
            throw new CartException("Product quantity exceeds available quantity!");
        }
    }

    @Override
    public int getCartQuantity(User user) {
        Cart cart = cartRepository.getCartByUser(user);
        return cart.getQuantity();
    }

    @Override
    public CartDTO getUsersCart(User user) {
        // Get cart
        Cart cart = cartRepository.getCartByUser(user);

        List<CartItemDTO> cartItemDTOS = cart.getCartItem().stream().map(
                cartItem -> modelMapper.map(cartItem, CartItemDTO.class)).collect(Collectors.toList()
        );



        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setCartItems(cartItemDTOS);
        return cartDTO;
    }

}
