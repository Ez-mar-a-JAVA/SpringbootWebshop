package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.category.CategoryDTO;
import com.EzmarJava.Webshop.dto.product.ProductDTO;
import com.EzmarJava.Webshop.service.CategoryService;
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
    private final CategoryService categoryService;

    public ProductPageController(ProductService productService, CategoryService categoryService)
    {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public String showProductPage(
            @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size, @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(required = false) Long categoryId,
            Model model
    ) {
        String sortField = sort[0];
        String sortDirection = sort[1];
        Page<ProductDTO> productsPage;

        if(categoryId == null) {
            productsPage = productService.findProducts(page, size, sortDirection, sortField, keyword);
        }else {
            productsPage = productService.findProductsByCategoryId(page, size, sortDirection, sortField, categoryId);
        }

        List<ProductDTO> products = productsPage.getContent();

        List<CategoryDTO> categories = categoryService.getAllCategories();

        model.addAttribute("keyword", keyword);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", productsPage.getNumber() + 1);
        model.addAttribute("totalItems", productsPage.getTotalElements());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("selectedCategoryId", categoryId); // Pass the selected category ID to the view
        model.addAttribute("categories", categories); // Pass all categories to the view

        return "product/productpage";
    }
}
