package br.com.stoom.resources;

import br.com.stoom.dto.response.FindAllBrandResponse;
import br.com.stoom.dto.response.FindAllCategoryResponse;
import br.com.stoom.mapper.BrandMapper;
import br.com.stoom.mapper.CategoryMapper;
import br.com.stoom.resources.docs.CategoryAPI;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.stoom.services.category.CategoryService;
import br.com.stoom.dto.request.UpdateCategoryRequest;
import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateCategoryRequestSet;
import br.com.stoom.dto.response.FindCategoryByIdResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource implements CategoryAPI {

    private final CategoryService categoryService;

    @Autowired
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @PostMapping("/v1")
    public ResponseEntity<Void> createCategories(@Validated CreateCategoryRequestSet createCategoriesRequest) {

        final var categories = CategoryMapper.toEntity(createCategoriesRequest);
        categoryService.createCategory(categories);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/v1")
    public ResponseEntity<Void> changeCategoryStatus(@Validated ChangeStatusRequestSet changeStatusRequestSet) {

        final var status = CategoryMapper.toService(changeStatusRequestSet);

        categoryService.changeCategoryStatus(status);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/v1")
    public ResponseEntity<Void> updateCategory(@Validated UpdateCategoryRequest updateCategoryRequest) {

        final var category = CategoryMapper.toEntity(updateCategoryRequest);
        categoryService.updateCategory(category);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/v1/{categoryId}")
    public ResponseEntity<FindCategoryByIdResponse> findCategoryById(Long categoryId) {

        final var category = CategoryMapper.toResponse(categoryService.findCategoryById(categoryId));

        return ResponseEntity.ok(category);
    }

    @Override
    @DeleteMapping("/v1")
    public ResponseEntity<Void> removeCategoryById(Set<Long> categoryIds) {

        categoryService.deleteCategory(categoryIds);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/v1")
    public ResponseEntity<Pagination<FindAllCategoryResponse>> findAllCategories(
            String categoryName,
            int page,
            int size,
            String sort,
            String direction
    ) {
        final var query = SearchQuery.with(
                page,
                size,
                sort,
                direction
        );

        final var brands = CategoryMapper.toResponse(categoryService.findAllCategories(categoryName, query));

        return ResponseEntity.ok(brands);
    }

}
