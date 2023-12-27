package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.product.CreateProductDTO;
import com.EzmarJava.Webshop.dto.product.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService
{
    void createProduct(CreateProductDTO createProductDTO);
    List<ProductDTO> findAllProducts();
    Page<ProductDTO> findProducts(int page, int size, String sortDirection, String sortField, String keyword);
    Page<ProductDTO> findProductsByCategoryId(int page, int size, String sortDirection, String sortField, Long categoryId);
}
