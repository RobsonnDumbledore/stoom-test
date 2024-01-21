package br.com.stoom.services.product;

import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import jakarta.transaction.Transactional;
import br.com.stoom.entities.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import br.com.stoom.exceptions.NotFoundException;
import org.springframework.data.domain.PageRequest;
import br.com.stoom.repositories.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import br.com.stoom.services.GenericBatchUpdateService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final GenericBatchUpdateService batchUpdateService;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            GenericBatchUpdateService batchUpdateService
    ) {
        this.productRepository = productRepository;
        this.batchUpdateService = batchUpdateService;
    }


    @Override
    public void createProduct(Set<ProductEntity> productsEntity) {

        logger.info("Starting creation of products. Number of products to be created: {}", productsEntity.size());

        this.productRepository.saveAll(productsEntity);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateProduct(ProductEntity productEntity) {

        logger.info("Updating product with ID: {}", productEntity.getId());

        ProductEntity existingProduct = findProductById(productEntity.getId());
        existingProduct.updateEntity(productEntity);
        this.productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void changeProductStatus(Map<Long, Boolean> changeStatus) {

        logger.info("Starting status change for {} products.", changeStatus.size());

        final var query = "UPDATE product SET active = :isActive WHERE id = :productId";
        this.batchUpdateService.executeBatchUpdate(query, changeStatus, "productId", ProductEntity.class);
    }

    @Override
    @Cacheable(cacheNames = "findProductById", key = "#productId")
    public ProductEntity findProductById(Long productId) {

        logger.info("Searching for product with ID: {}", productId);

        return this.productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found for ID: " + productId));
    }

    @Override
    @Cacheable(cacheNames = "findAllProducts", key = "{#productName, #searchQuery}")
    public Pagination<ProductEntity> findAllProducts(String productName, SearchQuery searchQuery) {

        logger.info("Finding all products");

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var products = this.productRepository.findAllProducts(productName, page);

        return new Pagination<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages()
        );
    }

    @Override
    @Cacheable(cacheNames = "findProductByCategory", key = "{#category, #searchQuery}")
    public Pagination<ProductEntity> findProductByCategory(String category, SearchQuery searchQuery) {

        logger.info("Finding products by category '{}'", category);

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var products = this.productRepository.findByCategory(category, page);

        return new Pagination<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages()
        );
    }

    @Override
    @Cacheable(cacheNames = "findProductByBrand", key = "{#brand, #searchQuery}")
    public Pagination<ProductEntity> findProductByBrand(String brand, SearchQuery searchQuery) {

        logger.info("Finding products by brand '{}'", brand);

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var products = this.productRepository.findByBrand(brand, page);

        return new Pagination<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages()
        );
    }
}
