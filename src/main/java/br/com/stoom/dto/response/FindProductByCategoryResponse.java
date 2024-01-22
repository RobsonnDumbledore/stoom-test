package br.com.stoom.dto.response;

public record FindProductByCategoryResponse(
        Long id,
        String name,
        String description,
        Double discount,
        String imageName,
        String sku,
        Double price,
        boolean active
) {
}
