package br.com.stoom.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateBrandRequest(
        @NotNull(message = "id is required")
        Long id,
        @NotBlank(message = "name is required")
        String name,
        boolean active
) {
}
