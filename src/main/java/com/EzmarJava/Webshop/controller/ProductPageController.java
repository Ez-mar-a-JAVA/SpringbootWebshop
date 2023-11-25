package com.EzmarJava.Webshop.controller;


import com.EzmarJava.Webshop.model.Product;
import com.EzmarJava.Webshop.service.impl.ProductPageServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

@RequestMapping("/home")
public class ProductPageController {

    private ProductPageServiceImpl productService;

    public String getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Product> products = productService.getAllProducts(pageable);

        model.addAttribute("products", products);

        return "productList"; // Return the name of the view (productList.html in this case)
    }

}
