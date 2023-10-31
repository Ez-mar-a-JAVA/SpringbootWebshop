package com.EzmarJava.Webshop.repository;

import com.EzmarJava.Webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
}
