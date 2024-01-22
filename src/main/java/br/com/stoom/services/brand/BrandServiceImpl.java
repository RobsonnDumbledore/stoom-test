package br.com.stoom.services.brand;

import java.util.Map;
import java.util.Set;

import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.stoom.entities.BrandEntity;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import br.com.stoom.exceptions.NotFoundException;
import br.com.stoom.repositories.BrandRepository;
import br.com.stoom.services.GenericBatchUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

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
    @Transactional
    public void deleteBrand(Set<Long> brandIds) {

        logger.info("Deleting brands for : {}", brandIds.size());

        if (!CollectionUtils.isEmpty(brandIds)) {
            this.brandRepository.deleteByIdIn(brandIds);
        }

    }

    @Override
    @Cacheable(cacheNames = "findAllBrands", key = "{#brandName, #searchQuery}")
    public Pagination<BrandEntity> findAllBrands(String brandName, SearchQuery searchQuery) {

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var products = this.brandRepository.findAllBrands(brandName, page);

        return new Pagination<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages()
        );
    }

    @Override
    public BrandEntity findBrandById(Long brandId) {

        logger.info("Searching for brand with ID: {}", brandId);

        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand not found for ID: " + brandId));
    }
}
