package com.EzmarJava.Webshop.service;

public interface StripeService {
    String createCharge(String email, String token, int amount);
}
