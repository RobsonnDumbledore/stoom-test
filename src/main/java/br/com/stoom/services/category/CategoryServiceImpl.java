package br.com.stoom.services.category;

import java.util.Set;
import java.util.Map;

import br.com.stoom.entities.BrandEntity;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import br.com.stoom.entities.CategoryEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import br.com.stoom.exceptions.NotFoundException;
import br.com.stoom.repositories.CategoryRepository;
import br.com.stoom.services.GenericBatchUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final GenericBatchUpdateService batchUpdateService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            GenericBatchUpdateService batchUpdateService
    ) {
        this.categoryRepository = categoryRepository;
        this.batchUpdateService = batchUpdateService;
    }

    @Override
    public void createCategory(Set<CategoryEntity> categoriesEntity) {

        logger.info("Starting creation of categories. Number of categories to be created: {}", categoriesEntity.size());

        this.categoryRepository.saveAllAndFlush(categoriesEntity);
    }

    @Override
    @Transactional
    public void changeCategoryStatus(Map<Long, Boolean> categoryStatus) {

        logger.info("Starting status change for {} categories.", categoryStatus.size());

        final var query = "UPDATE category SET active = :isActive WHERE id = :categoryId";
        this.batchUpdateService.executeBatchUpdate(query, categoryStatus, "categoryId", CategoryEntity.class);
    }

    @Override
    public void updateCategory(CategoryEntity categoryEntity) {

        logger.info("Updating category with ID: {}", categoryEntity.getId());

        this.categoryRepository.save(categoryEntity);
    }

    @Override
    @Transactional
    public void deleteCategory(Set<Long> categoryIds) {

        logger.info("Remove categories for : {}", categoryIds.size());

        if (!CollectionUtils.isEmpty(categoryIds)) {
            this.categoryRepository.deleteByIdIn(categoryIds);
        }
    }

    @Override
    @Cacheable(cacheNames = "findAllCategories", key = "{#categoryName, #searchQuery}")
    public Pagination<CategoryEntity> findAllCategories(String categoryName, SearchQuery searchQuery) {

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var products = this.categoryRepository.findAllCategories(categoryName, page);

        return new Pagination<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages()
        );
    }

    @Override
    public CategoryEntity findCategoryById(Long categoryId) {

        logger.info("Searching for category with ID: {}", categoryId);

        return this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found for ID: " + categoryId));
    }
}
