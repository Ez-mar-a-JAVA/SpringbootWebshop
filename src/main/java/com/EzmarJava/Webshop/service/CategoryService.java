package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.category.CategoryDTO;
import com.EzmarJava.Webshop.dto.category.CreateCategoryDTO;
import com.EzmarJava.Webshop.dto.category.UpdateCategoryDTO;

import java.util.List;

public interface CategoryService
{
    void createCategory(CreateCategoryDTO createCategoryDTO);
    void updateCategory(UpdateCategoryDTO updateCategoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    void deleteCategory(Long id);
}
