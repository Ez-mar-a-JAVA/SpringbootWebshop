package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.dto.product.CreateProductDTO;
import com.EzmarJava.Webshop.dto.product.ProductDTO;
import com.EzmarJava.Webshop.dto.product.UpdateProductDTO;
import com.EzmarJava.Webshop.model.Product;
import com.EzmarJava.Webshop.repository.ProductRepository;
import com.EzmarJava.Webshop.service.FileStorageService;
import com.EzmarJava.Webshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

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

    @Override

    public Page<ProductDTO> findProducts(int page, int size, String sortDirection, String sortField, String keyword) {
        Direction direction = sortDirection.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
        Order order = new Order(direction, sortField);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

        Page<Product> pageProducts;
        if(keyword == null) {
            pageProducts = productRepository.findAll(pageable);
        }else {
            pageProducts = productRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }

        return pageProducts.map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @Override
    public Page<ProductDTO> findProductsByCategoryId(int page, int size, String sortDirection, String sortField, Long categoryId)
    {
        Direction direction = sortDirection.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
        Order order = new Order(direction, sortField);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

        Page<Product> pageProducts = productRepository.findByCategory_Id(categoryId, pageable);

        return pageProducts.map(product -> modelMapper.map(product, ProductDTO.class));

    public void deleteProduct(Long productId)
    {
        productRepository.deleteById(productId);
    }

    @Override
    public void updateProduct(UpdateProductDTO updateProductDTO)
    {
        Product product = productRepository.getById(updateProductDTO.getId());
        MultipartFile image = null;

        // Get image from DTO
        if(!updateProductDTO.getImage().isEmpty())
        {
             image = updateProductDTO.getImage();
        }


        if(image != null)
        {
            // Store file on disk by relative path
            String filePath = fileStorageService.store(image, "src/main/resources/static/images/");

            if(!product.getImage().equalsIgnoreCase(filePath))
            {
                // Set image path
                product.setImage(filePath);
            }
        }


        product.setTitle(updateProductDTO.getTitle());
        product.setDescription(updateProductDTO.getDescription());
        product.setPrice(updateProductDTO.getPrice());
        product.setCategory(updateProductDTO.getCategory());


        // Save product to DB
        productRepository.save(product);
    }

    @Override
    public ProductDTO getById(Long productId)
    {
        return modelMapper.map(productRepository.getById(productId), ProductDTO.class);

    }
}
