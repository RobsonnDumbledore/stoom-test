package br.com.stoom.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;

public record UpdateProductRequest(

        @NotNull(message = "id is required")
        Long id,

        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "description is required")
        String description,

        @PositiveOrZero(message = "price should be equal or greater than zero")
        @NotNull(message = "price is required")
        Double price,

        @PositiveOrZero(message = "discount should be positive or zero")
        @Max(value = 100, message = "discount should be between 0 and 100")
        Double discount,

        String imageName,

        String sku,

        boolean active,

        @NotNull(message = "brandId is required")
        Long brandId,

        @NotNull(message = "categoryIds is required")
        Set<Long> categoryIds
) {
}
