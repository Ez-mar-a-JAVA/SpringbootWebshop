package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.product.CreateProductDTO;
import com.EzmarJava.Webshop.service.CategoryService;
import com.EzmarJava.Webshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;


@Controller
public class ProductController
{
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService)
    {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/create-product")
    public String createProductPage(Model model)
    {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("product", new CreateProductDTO());
        return "product/create_product";
    }

    @PostMapping("/admin/create-product")
    public String createProduct(@Valid @ModelAttribute("product") CreateProductDTO createProductDTO, BindingResult bindingResult, Model model) throws IOException
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("product", new CreateProductDTO());
            return "product/create_product";
        }

        productService.createProduct(createProductDTO);

        return "redirect:/admin/create-product";
    }
}
