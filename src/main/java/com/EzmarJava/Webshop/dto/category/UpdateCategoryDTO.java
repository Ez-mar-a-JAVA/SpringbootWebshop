package com.EzmarJava.Webshop.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDTO
{
    private Long id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

}
