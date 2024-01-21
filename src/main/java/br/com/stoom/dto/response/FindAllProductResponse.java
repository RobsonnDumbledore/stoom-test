package br.com.stoom.dto.response;

public record FindAllProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        boolean active
) {
}
