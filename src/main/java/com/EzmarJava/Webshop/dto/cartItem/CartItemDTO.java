package com.EzmarJava.Webshop.dto.cartItem;

import com.EzmarJava.Webshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDTO {
    private Long id;
    private int quantity;
    private Product product;
}
