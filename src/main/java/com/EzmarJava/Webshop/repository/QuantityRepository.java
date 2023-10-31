package com.EzmarJava.Webshop.repository;

import com.EzmarJava.Webshop.model.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<Quantity,Long> {
    Quantity getById(Long id);

    Quantity findByProductId(Long productId);

}
