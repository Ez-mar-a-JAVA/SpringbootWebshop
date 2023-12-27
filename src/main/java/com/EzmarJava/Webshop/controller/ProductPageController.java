package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.product.ProductDTO;
import com.EzmarJava.Webshop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductPageController {

    private final ProductService productService;

    public ProductPageController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String showProductPage(
            @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size, @RequestParam(defaultValue = "id,asc") String[] sort,
            Model model
    ) {
        String sortField = sort[0];
        String sortDirection = sort[1];
        Page<ProductDTO> productsPage = productService.findProducts(page, size, sortDirection, sortField, keyword);
        List<ProductDTO> products = productsPage.getContent();

        model.addAttribute("keyword", keyword);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", productsPage.getNumber() + 1);
        model.addAttribute("totalItems", productsPage.getTotalElements());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return "product/productpage";
    }
}
