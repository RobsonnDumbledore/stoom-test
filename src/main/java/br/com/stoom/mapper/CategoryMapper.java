package br.com.stoom.mapper;

import br.com.stoom.dto.request.*;
import br.com.stoom.dto.response.FindCategoryByIdResponse;
import br.com.stoom.entities.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface CategoryMapper {

    static CategoryEntity toEntity(Long id) {
        return new CategoryEntity(id);
    }

    static Set<CategoryEntity> toEntity(Set<Long> categoriesId) {

        return categoriesId
                .stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toSet());
    }

    static CategoryEntity toEntity(CreateCategoryRequest createCategoryRequest) {
        return new CategoryEntity(
                createCategoryRequest.name(),
                createCategoryRequest.active()
        );
    }

    static CategoryEntity toEntity(UpdateCategoryRequest updateCategoryRequest) {
        return new CategoryEntity(
                updateCategoryRequest.id(),
                updateCategoryRequest.name()
        );
    }

    static Set<CategoryEntity> toEntity(CreateCategoryRequestSet createCategoriesRequest) {

        return createCategoriesRequest.categories()
                .stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toSet());
    }

    static FindCategoryByIdResponse toResponse(CategoryEntity categoryEntity) {

        return new FindCategoryByIdResponse(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.isActive()
        );

    }

    static Map<Long, Boolean> toService(ChangeStatusRequestSet changeStatusRequestSet) {

        return changeStatusRequestSet.status()
                .stream()
                .collect(Collectors.toMap(
                        ChangeStatusRequest::id,
                        ChangeStatusRequest::isActive
                ));
    }

}
