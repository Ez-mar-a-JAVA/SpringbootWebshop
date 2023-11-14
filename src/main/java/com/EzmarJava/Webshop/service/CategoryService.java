package com.EzmarJava.Webshop.service;

import com.EzmarJava.Webshop.dto.CategoryDTO;
import com.EzmarJava.Webshop.dto.CreateCategoryDTO;

import java.util.List;

public interface CategoryService
{
    void createCategory(CreateCategoryDTO createCategoryDTO);
    List<CategoryDTO> getAllCategories();
    void deleteCategory(Long id);
}
