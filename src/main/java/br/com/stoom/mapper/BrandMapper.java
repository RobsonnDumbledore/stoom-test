package br.com.stoom.mapper;

import br.com.stoom.dto.request.*;
import br.com.stoom.dto.response.FindAllBrandResponse;
import br.com.stoom.dto.response.FindBrandByIdResponse;
import br.com.stoom.dto.response.FindProductByCategoryResponse;
import br.com.stoom.entities.BrandEntity;
import br.com.stoom.entities.ProductEntity;
import br.com.stoom.utils.Pagination;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface BrandMapper {

    static Set<BrandEntity> toEntity(CreateBrandRequestSet createBrandsRequest) {

        return createBrandsRequest.brands()
                .stream()
                .map(brand -> new BrandEntity(
                         brand.name(),
                         brand.active()
                ))
                .collect(Collectors.toSet());
    }

    static BrandEntity toEntity(UpdateBrandRequest updateBrandRequest) {
        return new BrandEntity(
                updateBrandRequest.id(),
                updateBrandRequest.name()
        );
    }

    static BrandEntity toEntity(Long brandId) {
        return new BrandEntity(brandId);
    }

    static FindBrandByIdResponse toResponse(BrandEntity brandEntity) {

        return new FindBrandByIdResponse(
                brandEntity.getId(),
                brandEntity.getName(),
                brandEntity.isActive()
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

    static Pagination<FindAllBrandResponse> toResponse(Pagination<BrandEntity> pagination) {

        return new Pagination<>(
                pagination.content()
                        .stream()
                        .map(p -> new FindAllBrandResponse(
                                p.getId(),
                                p.getName(),
                                p.isActive()
                        ))
                        .collect(Collectors.toList()),
                pagination.currentPage(),
                pagination.size(),
                pagination.totalElements(),
                pagination.totalPages()
        );
    }

}
