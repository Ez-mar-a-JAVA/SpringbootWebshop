package com.EzmarJava.Webshop.dto.cart;

import com.EzmarJava.Webshop.dto.cartItem.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private int quantity;
    private List<CartItemDTO> cartItems;
}
