package br.com.stoom.dto.response;

public record FindAllCategoryResponse(
        Long id,
        String name,
        boolean active
) {
}
