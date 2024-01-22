package br.com.stoom.resources;

import br.com.stoom.dto.response.FindAllBrandResponse;
import br.com.stoom.mapper.BrandMapper;
import br.com.stoom.resources.docs.BrandAPI;
import br.com.stoom.services.brand.BrandService;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.stoom.dto.request.UpdateBrandRequest;
import br.com.stoom.dto.request.CreateBrandRequestSet;
import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.response.FindBrandByIdResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@RestController
@RequestMapping("/api/brands")
public class BrandResource implements BrandAPI {

    private final BrandService brandService;

    @Autowired
    public BrandResource(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    @PostMapping("/v1")
    public ResponseEntity<Void> createBrands(@Validated CreateBrandRequestSet createBrandsRequest) {

        final var brand = BrandMapper.toEntity(createBrandsRequest);
        brandService.createBrand(brand);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/v1/{brandId}")
    public ResponseEntity<FindBrandByIdResponse> findBrandById(Long brandId) {

        final var brand = BrandMapper.toResponse(brandService.findBrandById(brandId));

        return ResponseEntity.ok(brand);
    }

    @Override
    @DeleteMapping("/v1")
    public ResponseEntity<Void> removeBrandById(Set<Long> brandIds) {

        brandService.deleteBrand(brandIds);

        return ResponseEntity.ok().build();
    }

    @Override
    @PatchMapping("/v1")
    public ResponseEntity<Void> changeStatusBrand(@Validated ChangeStatusRequestSet changeStatusRequest) {

        final var status = BrandMapper.toService(changeStatusRequest);
        brandService.changeBrandStatus(status);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/v1")
    public ResponseEntity<Void> updateBrand(@Validated UpdateBrandRequest updateBrandRequest) {

        final var brand = BrandMapper.toEntity(updateBrandRequest);
        brandService.updateBrand(brand);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/v1")
    public ResponseEntity<Pagination<FindAllBrandResponse>> findAllBrands(
            String brandName,
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

        final var brands = BrandMapper.toResponse(brandService.findAllBrands(brandName, query));

        return ResponseEntity.ok(brands);
    }

}
