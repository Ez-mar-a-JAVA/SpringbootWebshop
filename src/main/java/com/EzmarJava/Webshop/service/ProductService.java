package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.product.CreateProductDTO;
import com.EzmarJava.Webshop.dto.product.ProductDTO;

import java.util.List;

public interface ProductService
{
    void createProduct(CreateProductDTO createProductDTO);
    List<ProductDTO> findAllProducts();
}
