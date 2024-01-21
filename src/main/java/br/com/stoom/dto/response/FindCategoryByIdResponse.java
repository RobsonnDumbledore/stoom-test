package br.com.stoom.dto.response;

public record FindCategoryByIdResponse(
        Long id,
        String name,
        boolean active
) {
}
