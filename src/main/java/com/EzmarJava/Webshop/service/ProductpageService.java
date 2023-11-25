package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductpageService {

    public Page<Product> getAllProducts(Pageable pageable);

}
