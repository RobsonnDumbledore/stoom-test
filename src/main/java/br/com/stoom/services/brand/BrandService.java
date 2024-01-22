package br.com.stoom.services.brand;

import br.com.stoom.entities.BrandEntity;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;

import java.util.Map;
import java.util.Set;

public interface BrandService {

    void createBrand(Set<BrandEntity> brandsEntity);
    void changeBrandStatus(Map<Long, Boolean> brandStatus);
    void updateBrand(BrandEntity brandEntity);
    void deleteBrand(Set<Long> brandIds);
    Pagination<BrandEntity> findAllBrands(String brandName, SearchQuery searchQuery);
    BrandEntity findBrandById(Long brandId);

}
