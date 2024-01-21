package br.com.stoom.dto.request;

import jakarta.validation.Valid;

import java.util.Set;

public record CreateCategoryRequestSet(
        @Valid
        Set<CreateCategoryRequest> categories
) {
}
