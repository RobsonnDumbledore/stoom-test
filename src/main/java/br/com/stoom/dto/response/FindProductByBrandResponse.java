package br.com.stoom.dto.response;

public record FindProductByBrandResponse(
        Long id,
        String name,
        String description,
        Double price,
        boolean active
) {
}
