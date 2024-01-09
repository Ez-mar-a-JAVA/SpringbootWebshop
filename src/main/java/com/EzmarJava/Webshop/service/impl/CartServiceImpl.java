package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.dto.cart.CartDTO;
import com.EzmarJava.Webshop.dto.cartItem.AddCartItemDTO;
import com.EzmarJava.Webshop.dto.cartItem.CartItemDTO;
import com.EzmarJava.Webshop.exception.CartException;
import com.EzmarJava.Webshop.model.Cart;
import com.EzmarJava.Webshop.model.CartItem;
import com.EzmarJava.Webshop.model.Product;
import com.EzmarJava.Webshop.model.User;
import com.EzmarJava.Webshop.repository.CartItemRepository;
import com.EzmarJava.Webshop.repository.CartRepository;
import com.EzmarJava.Webshop.repository.ProductRepository;
import com.EzmarJava.Webshop.repository.UserRepository;
import com.EzmarJava.Webshop.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository, ModelMapper modelMapper, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
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
        if(cart == null) {
            return 0;
        }
        return cart.getQuantity();
    }

    @Override
    public CartDTO getUsersCart(User user) {
        // Get cart
        Cart cart = cartRepository.getCartByUser(user);

        List<CartItemDTO> cartItemDTOS =
             cartItemDTOS = cart.getCartItem().stream().map(
                    cartItem -> modelMapper.map(cartItem, CartItemDTO.class)).collect(Collectors.toList()
            );


        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setCartItems(cartItemDTOS);
        return cartDTO;
    }

    @Override
    public int getCartTotal(User user) {
        // Get cart
        Cart cart = cartRepository.getCartByUser(user);

        // Get cart items
        List<CartItem> cartItems = cart.getCartItem();

        int total = 0;
        for(CartItem cartItem : cartItems) {
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public void deleteCartItem(Long cartItemId, User user) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException("Cart item not found with ID: " + cartItemId));

        Product product = cartItem.getProduct();

        // Remove the cart item from the user's cart
        cartItem.getCart().getCartItem().remove(cartItem);

        // Remove the association with the cart
        cartItem.setCart(null);
        cartItem.setProduct(null);

        // Delete the cart item from the database
        cartItemRepository.delete(cartItem);

        product.setQuantity(product.getQuantity() + cartItem.getQuantity());
        productRepository.save(product);

        // Get cart
        Cart cart = cartRepository.getCartByUser(user);
        cart.setQuantity(cart.getQuantity() - cartItem.getQuantity());
        cartRepository.save(cart);
    }

    @Override
    public void decreaseCartItem(Long cartItemId, User user) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException("Cart item not found with ID: " + cartItemId));

        Product product = cartItem.getProduct();

        if(cartItem.getQuantity() == 1) {
            deleteCartItem(cartItemId, user);
        } else {
            // Decrease item quantity
            Cart cart = cartRepository.getCartByUser(user);
            cart.setQuantity(cart.getQuantity() - 1);
            cartRepository.save(cart);

            product.setQuantity(product.getQuantity() + 1);
            productRepository.save(product);

            cartItem.setQuantity(cartItem.getQuantity()-1);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public void increaseCartItem(Long cartItemId, User user) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException("Cart item not found with ID: " + cartItemId));

        // Get product
        Product product = cartItem.getProduct();


        if(product.getQuantity() >= 1) {
            product.setQuantity(product.getQuantity() - 1);
            productRepository.save(product);


            // Increase
            Cart cart = cartRepository.getCartByUser(user);
            cart.setQuantity(cart.getQuantity()+1);
            cartRepository.save(cart);

            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItemRepository.save(cartItem);
        } else {
            // Show error because there aren't enough products
            throw new CartException("Cannot increase cart item quantity, not enough products!");
        }
    }
}
