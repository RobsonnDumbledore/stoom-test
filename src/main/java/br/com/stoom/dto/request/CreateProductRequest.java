package br.com.stoom.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateProductRequest(

        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "description is required")
        String description,
        @NotNull(message = "price is required")
        Double price,
        boolean active,
        @NotNull(message = "brandId is required")
        Long brandId,
        @NotNull(message = "categoryIds is required")
        Set<Long> categoryIds
) {}
