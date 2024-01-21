package br.com.stoom.services.brand;

import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.stoom.entities.BrandEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import br.com.stoom.exceptions.NotFoundException;
import br.com.stoom.repositories.BrandRepository;
import br.com.stoom.services.GenericBatchUpdateService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final GenericBatchUpdateService genericBatchUpdateService;
    private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    public BrandServiceImpl(
            BrandRepository brandRepository,
            GenericBatchUpdateService genericBatchUpdateService
    ) {
        this.brandRepository = brandRepository;
        this.genericBatchUpdateService = genericBatchUpdateService;
    }

    @Override
    public void createBrand(Set<BrandEntity> brandsEntity) {

        logger.info("Starting creation of brands. Number of brands to be created: {}", brandsEntity.size());

        this.brandRepository.saveAll(brandsEntity);
    }

    @Override
    @Transactional
    public void changeBrandStatus(Map<Long, Boolean> changeStatus) {

        logger.info("Starting status change for {} brands.", changeStatus.size());

        final var query = "UPDATE brand SET active = :isActive WHERE id = :brandId";
        genericBatchUpdateService.executeBatchUpdate(query, changeStatus, "brandId", BrandEntity.class);

    }

    @Override
    public void updateBrand(BrandEntity brandEntity) {

        logger.info("Updating brand with ID: {}", brandEntity.getId());

        findBrandById(brandEntity.getId());
        this.brandRepository.save(brandEntity);
    }

    @Override
    public BrandEntity findBrandById(Long brandId) {

        logger.info("Searching for brand with ID: {}", brandId);

        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand not found for ID: " + brandId));
    }
}
