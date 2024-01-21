package br.com.stoom.dto.response;

import java.util.Set;

public record FindProductByIdResponse(
        Long id,
        String name,
        String description,
        Double price,
        boolean active,
        BrandResponse brand,
        Set<CategoryResponse> categories
) {
}
