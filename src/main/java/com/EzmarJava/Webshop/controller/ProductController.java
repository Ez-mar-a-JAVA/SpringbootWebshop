package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.product.CreateProductDTO;
import com.EzmarJava.Webshop.dto.product.UpdateProductDTO;
import com.EzmarJava.Webshop.service.CategoryService;
import com.EzmarJava.Webshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/admin/products")
    public String products(Model model)
    {
        CacheControl.noCache();
        model.addAttribute("products", productService.findAllProducts());
        return "product/products_admin";
    }

    @PostMapping("/admin/delete-product/{productId}")
    public String deleteProduct(@PathVariable Long productId)
    {
        productService.deleteProduct(productId);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/edit-product/{id}")
    public String updateProductPage(@PathVariable Long id, Model model)
    {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("product", productService.getById(id));
        return "product/update_product";
    }

    @PostMapping("/admin/edit-product/{id}")
    public String updateProduct(@Valid @ModelAttribute("product") UpdateProductDTO updateProductDTO, @PathVariable Long id, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("product", productService.getById(id));
            return "product/update_product";
        }

        productService.updateProduct(updateProductDTO);
        return "redirect:/admin/products";
    }
}
