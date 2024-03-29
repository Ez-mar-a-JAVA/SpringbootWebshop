package com.EzmarJava.Webshop.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutUserDataDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String address;
}
