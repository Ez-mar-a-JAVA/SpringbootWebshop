package com.EzmarJava.Webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String title ;
    private String description;
    private double price ;
    private String image;

    private  int quantity;
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    private Category category;
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    @JoinColumn(name = "orderCol")
    private Order order;
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    private Cart cart;

}
