package br.com.stoom.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "name is required")
        String name,
        boolean active
) {
}
