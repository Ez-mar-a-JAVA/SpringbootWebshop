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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private  String title ;
   private String Description;
   private double price ;
    String image;
    //ezt máshogy kell https://stackoverflow.com/questions/21059451/cannot-declare-list-property-in-the-jpa-entity-class-it-says-basic-attribute
    //private List<String> galllery;
    private String gallery; 
    private  int quantity;
    @ManyToOne
    private Category category;
    @ManyToOne
     private Order order;


}
