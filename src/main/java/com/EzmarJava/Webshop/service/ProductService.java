package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.product.CreateProductDTO;

import java.io.IOException;

public interface ProductService
{
    void createProduct(CreateProductDTO createProductDTO) throws IOException;
}
