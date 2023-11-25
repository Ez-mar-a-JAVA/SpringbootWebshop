package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.model.Product;
import com.EzmarJava.Webshop.repository.ProductRepository;
import com.EzmarJava.Webshop.service.ProductpageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service

public class ProductPageServiceImpl implements ProductpageService {

    private final ProductRepository productRepository;

    public ProductPageServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }



}
