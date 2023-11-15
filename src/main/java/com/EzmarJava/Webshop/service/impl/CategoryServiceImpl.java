package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.dto.category.CategoryDTO;
import com.EzmarJava.Webshop.dto.category.CreateCategoryDTO;
import com.EzmarJava.Webshop.dto.category.UpdateCategoryDTO;
import com.EzmarJava.Webshop.model.Category;
import com.EzmarJava.Webshop.repository.CategoryRepository;
import com.EzmarJava.Webshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService
{
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository)
    {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(CreateCategoryDTO createCategoryDTO)
    {
        categoryRepository.save(modelMapper.map(createCategoryDTO, Category.class));
    }

    @Override
    public void updateCategory(UpdateCategoryDTO updateCategoryDTO)
    {
        // TODO: Exception handling
        Category existingCategory = categoryRepository.findById(updateCategoryDTO.getId()).orElseThrow(NoSuchElementException::new);

        existingCategory = modelMapper.map(updateCategoryDTO, Category.class);
        categoryRepository.save(existingCategory);
    }


    @Override
    public List<CategoryDTO> getAllCategories()
    {
        return categoryRepository.findAll().stream().map(category -> modelMapper
                .map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id)
    {
        // TODO: Exception handling
        return modelMapper.map(categoryRepository.findById(id).orElseThrow(NoSuchElementException::new), CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Long id)
    {
        categoryRepository.deleteById(id);
    }
}
