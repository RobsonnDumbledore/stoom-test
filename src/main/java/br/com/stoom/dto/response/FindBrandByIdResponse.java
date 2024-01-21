package br.com.stoom.dto.response;

public record FindBrandByIdResponse(
        Long id,
        String name,
        boolean active
) {
}
