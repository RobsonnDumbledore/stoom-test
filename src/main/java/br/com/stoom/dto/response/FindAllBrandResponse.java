package br.com.stoom.dto.response;

public record FindAllBrandResponse(
        Long id,
        String name,
        boolean active
) {
}
