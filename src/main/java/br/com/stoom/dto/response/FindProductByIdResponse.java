package br.com.stoom.dto.response;

import java.util.Set;

public record FindProductByIdResponse(
        Long id,
        String name,
        String description,
        String imageName,
        String sku,
        Double discount,
        Double price,
        boolean active,
        BrandResponse brand,
        Set<CategoryResponse> categories
) {
}
