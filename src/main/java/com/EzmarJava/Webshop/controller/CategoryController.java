package com.EzmarJava.Webshop.controller;

import com.EzmarJava.Webshop.dto.CreateCategoryDTO;
import com.EzmarJava.Webshop.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController
{
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Shows Create Category page
    @GetMapping("/admin/create-category")
    public String createCategoryPage(Model model)
    {
        model.addAttribute("category", new CreateCategoryDTO());
        return "admin/create_category";
    }

    // Handles Create Category form
    @PostMapping("/admin/create-category")
    public String createCategory(@Valid @ModelAttribute("category") CreateCategoryDTO createCategoryDTO, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("category", createCategoryDTO);
            return "admin/create_category";
        }

        categoryService.createCategory(createCategoryDTO);
        return "redirect:/admin/create-category";
    }

    // Shows All Categories page
    @GetMapping("/admin/categories")
    public String categoriesPage(Model model)
    {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories";
    }

    // Handles Delete Category
    @PostMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id)
    {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
}
