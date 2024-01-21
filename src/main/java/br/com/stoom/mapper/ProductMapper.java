package br.com.stoom.mapper;

import br.com.stoom.dto.request.*;
import br.com.stoom.dto.response.*;
import br.com.stoom.entities.ProductEntity;
import br.com.stoom.utils.Pagination;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface ProductMapper {

    static ProductEntity toEntity(CreateProductRequest createProductRequest) {

        return new ProductEntity(
                createProductRequest.name(),
                createProductRequest.description(),
                createProductRequest.price(),
                createProductRequest.active(),
                BrandMapper.toEntity(createProductRequest.brandId()),
                CategoryMapper.toEntity(createProductRequest.categoryIds())
        );
    }

    static Set<ProductEntity> toEntity(CreateProductRequestSet createProductsRequest) {

        return createProductsRequest.products()
                .stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toSet());
    }

    static ProductEntity toEntity(UpdateProductRequest updateProductRequest) {
        return new ProductEntity(
                updateProductRequest.id(),
                updateProductRequest.name(),
                updateProductRequest.description(),
                updateProductRequest.price(),
                updateProductRequest.active(),
                BrandMapper.toEntity(updateProductRequest.brandId()),
                CategoryMapper.toEntity(updateProductRequest.categoryIds())
        );
    }

    static FindProductByIdResponse toResponse(ProductEntity productEntity) {

        final var brand = new BrandResponse(
                productEntity.getBrand().getId(),
                productEntity.getBrand().getName(),
                productEntity.getBrand().isActive()
        );

        final var categories = productEntity.getCategories()
                .stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.isActive())
                )
                .collect(Collectors.toSet());

        return new FindProductByIdResponse(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.isActive(),
                brand,
                categories
        );
    }


    static Pagination<FindProductByCategoryResponse> byCategoryResponse(Pagination<ProductEntity> pagination) {

        return new Pagination<>(
                pagination.content()
                        .stream()
                        .map(p -> new FindProductByCategoryResponse(
                                 p.getId(),
                                 p.getName(),
                                 p.getDescription(),
                                 p.getPrice(),
                                 p.isActive()
                        ))
                        .collect(Collectors.toList()),
                pagination.currentPage(),
                pagination.size(),
                pagination.totalElements(),
                pagination.totalPages()
        );
    }

    static Pagination<FindProductByBrandResponse> byBrandResponse(Pagination<ProductEntity> pagination) {

        return new Pagination<>(
                pagination.content()
                        .stream()
                        .map(p -> new FindProductByBrandResponse(
                                p.getId(),
                                p.getName(),
                                p.getDescription(),
                                p.getPrice(),
                                p.isActive()
                        ))
                        .collect(Collectors.toList()),
                pagination.currentPage(),
                pagination.size(),
                pagination.totalElements(),
                pagination.totalPages()
        );

    }


    static Pagination<FindAllProductResponse> byAllProductResponse(Pagination<ProductEntity> pagination) {
        return new Pagination<>(
                pagination.content()
                       .stream()
                       .map(p -> new FindAllProductResponse(
                                p.getId(),
                                p.getName(),
                                p.getDescription(),
                                p.getPrice(),
                                p.isActive()
                        ))
                       .collect(Collectors.toList()),
                pagination.currentPage(),
                pagination.size(),
                pagination.totalElements(),
                pagination.totalPages()
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
