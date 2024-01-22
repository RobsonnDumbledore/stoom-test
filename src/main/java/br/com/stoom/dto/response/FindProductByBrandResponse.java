package br.com.stoom.dto.response;

public record FindProductByBrandResponse(
        Long id,
        String name,
        String description,
        String imageName,
        String sku,
        Double price,
        Double discount,
        boolean active
) {
}
