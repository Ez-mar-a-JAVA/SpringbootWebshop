package com.EzmarJava.Webshop.repository;

import com.EzmarJava.Webshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category getById(Long id);
    Category findByName(String name);


}
