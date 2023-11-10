package com.EzmarJava.Webshop.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long productId;
    private int quantity ;
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    Cart cart;
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    @JoinColumn(name = "orderCol")
    Order order;



}
