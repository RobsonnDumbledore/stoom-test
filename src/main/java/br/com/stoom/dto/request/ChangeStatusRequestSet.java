package br.com.stoom.dto.request;

import jakarta.validation.Valid;

import java.util.Set;

public record
ChangeStatusRequestSet(
        @Valid
        Set<ChangeStatusRequest> status
) {
}
