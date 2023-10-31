package com.EzmarJava.Webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private String address;
    @OneToMany
   private Set<Order>orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> authorities;

    // Custom constructor for data seeding
    public User(String username, String email, String password, Set<Role> authorities)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
}
