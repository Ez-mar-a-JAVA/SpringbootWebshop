package com.EzmarJava.Webshop.repository;

import com.EzmarJava.Webshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getById(Long id );

    Product findByTitle(String title);
    Product findByDescription(String description);

}
