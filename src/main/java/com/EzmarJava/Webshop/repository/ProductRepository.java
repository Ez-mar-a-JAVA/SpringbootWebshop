package com.EzmarJava.Webshop.repository;

import com.EzmarJava.Webshop.model.Category;
import com.EzmarJava.Webshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getById(Long id );
    Product findByTitle(String title);
    Product findByDescription(String description);
    List<Product> findAllByCategory(Category category);
    Page<Product> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);
}
