package com.EzmarJava.Webshop.dto.product;

import com.EzmarJava.Webshop.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO
{
    private Long id;
    private String title ;
    private String description;
    private double price ;
    private String image;
    private  int quantity;
    private Category category;
}
