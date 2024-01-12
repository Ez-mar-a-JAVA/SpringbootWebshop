package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.service.StripeService;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeServiceImpl implements StripeService {
    @Override
    public String createCharge(String email, String token, int amount) {
        String id = null;

        try {
            Stripe.apiKey = "sk_test_51OXVOhBvafzlZiOshcpsLhrC7ViYxFrj12iQK2sMpabNKEYBgdR889zvgutgPOMROlP0ONwMF7DptG3kPY2uw56Q002dqei2SK";
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "usd");
            chargeParams.put("description", "Charge for " + email);
            chargeParams.put("source", token); // ^ obtained with Stripe.js

            //create a charge
            Charge charge = Charge.create(chargeParams);
            id = charge.getId();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }
}
