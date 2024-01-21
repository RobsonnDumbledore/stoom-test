package br.com.stoom.dto.request;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

public record CreateBrandRequestSet(
        @Valid
        Set<CreateBrandRequest> brands
) {
}
