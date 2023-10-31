package com.EzmarJava.Webshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO
{
    private Long id;

    @NotEmpty(message = "Username must not be empty!")
    private String username;

    @NotEmpty(message = "Firstname must not be empty!")
    private String firstname;

    @NotEmpty(message = "Lastname must not be empty!")
    private String lastname;

    @NotEmpty(message = "Email must not be empty!")
    @Email(message = "Email must be valid!")
    private String email;

    @NotEmpty(message = "Password must not be empty!")
    private String password;
}
