package br.com.stoom.dto.response;

public record BrandResponse(
        Long id,
        String name,
        boolean active
) {
}
