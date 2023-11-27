package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.product.CreateProductDTO;
import com.EzmarJava.Webshop.dto.product.ProductDTO;
import com.EzmarJava.Webshop.dto.product.UpdateProductDTO;

import java.util.List;

public interface ProductService
{
    void createProduct(CreateProductDTO createProductDTO);
    List<ProductDTO> findAllProducts();
    void deleteProduct(Long productId);
    void updateProduct(UpdateProductDTO updateProductDTO);
    ProductDTO getById(Long productId);
}
