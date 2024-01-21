package com.EzmarJava.Webshop.dto.cartItem;

import com.EzmarJava.Webshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemDTO
{
    private Long productId;
    private int quantity = 1;
}
