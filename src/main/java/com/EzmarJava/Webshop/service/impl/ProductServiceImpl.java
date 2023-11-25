package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.dto.product.CreateProductDTO;
import com.EzmarJava.Webshop.dto.product.ProductDTO;
import com.EzmarJava.Webshop.model.Product;
import com.EzmarJava.Webshop.repository.ProductRepository;
import com.EzmarJava.Webshop.service.FileStorageService;
import com.EzmarJava.Webshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, FileStorageService fileStorageService)
    {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void createProduct(CreateProductDTO createProductDTO)
    {
        // Get image from DTO
        MultipartFile image = createProductDTO.getImage();

        // Store file on disk by relative path
        String filePath = fileStorageService.store(image, "src/main/resources/static/images/");

        Product product = modelMapper.map(createProductDTO, Product.class);

        // Set image path
        product.setImage(filePath);

        // Save product to DB
        productRepository.save(product);
    }

    @Override
    public List<ProductDTO> findAllProducts()
    {
        return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
    }
}
