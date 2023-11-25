package com.EzmarJava.Webshop.controller;



import com.EzmarJava.Webshop.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller


public class ProductPageController {


    @Autowired
    private  ProductServiceImpl productService;

    @GetMapping("productpage")
    public String loadPruductPage(Model model){
        model.addAttribute("products",productService.findAllProducts());
        return "/product/productpage";

    }



}
