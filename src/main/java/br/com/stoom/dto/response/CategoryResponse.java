package br.com.stoom.dto.response;

public record CategoryResponse(
        Long id,
        String name,
        boolean active
) {
}
