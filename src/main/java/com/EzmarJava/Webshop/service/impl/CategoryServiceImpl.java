package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.dto.CategoryDTO;
import com.EzmarJava.Webshop.dto.CreateCategoryDTO;
import com.EzmarJava.Webshop.model.Category;
import com.EzmarJava.Webshop.repository.CategoryRepository;
import com.EzmarJava.Webshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<CategoryDTO> getAllCategories()
    {
        return categoryRepository.findAll().stream().map(category -> modelMapper
                .map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id)
    {
        categoryRepository.deleteById(id);
    }
}
