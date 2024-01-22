package br.com.stoom.dto.response;

public record FindAllProductResponse(
        Long id,
        String name,
        String description,
        String imageName,
        String sku,
        Double discount,
        Double price,
        boolean active
) {
}
