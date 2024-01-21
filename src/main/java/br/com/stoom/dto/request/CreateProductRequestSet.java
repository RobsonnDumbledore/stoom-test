package br.com.stoom.dto.request;

import jakarta.validation.Valid;

import java.util.Set;

public record CreateProductRequestSet(
        @Valid
        Set<CreateProductRequest> products
) {
}
