package com.EzmarJava.Webshop.dto.product;

import com.EzmarJava.Webshop.model.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO
{
    @NotEmpty(message = "Title must not be empty!")
    private String title;

    @NotEmpty(message = "Description must not be empty!")
    private String description;

    private double price;

    private MultipartFile image;

    private  int quantity;

    private Category category;
}
