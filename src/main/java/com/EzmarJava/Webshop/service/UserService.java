package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.RegistrationDTO;
import com.EzmarJava.Webshop.model.User;

public interface UserService
{
    void createUser(RegistrationDTO registrationDTO);

    User findByUsername(String username);
}
