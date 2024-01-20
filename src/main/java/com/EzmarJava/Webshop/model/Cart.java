package com.EzmarJava.Webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItem;

    @OneToOne(mappedBy = "cart")
    private User user; // Change this field

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity); // Include relevant fields, excluding circular references
    }
}
