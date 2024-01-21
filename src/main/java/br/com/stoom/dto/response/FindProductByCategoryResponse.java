package br.com.stoom.dto.response;

public record FindProductByCategoryResponse(
        Long id,
        String name,
        String description,
        Double price,
        boolean active
) {
}
