package br.com.stoom.services.brand;

import br.com.stoom.entities.BrandEntity;

import java.util.Map;
import java.util.Set;

public interface BrandService {

    void createBrand(Set<BrandEntity> brandsEntity);
    void changeBrandStatus(Map<Long, Boolean> brandStatus);
    void updateBrand(BrandEntity brandEntity);
    BrandEntity findBrandById(Long brandId);

}
