package br.com.stoom.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequest(
        @NotNull(message = "id is required")
        Long id,
        boolean isActive
) {
}
