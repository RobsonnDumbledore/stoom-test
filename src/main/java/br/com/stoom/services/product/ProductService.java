package br.com.stoom.services.product;

import br.com.stoom.entities.ProductEntity;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Set;

public interface ProductService {

    void createProduct(Set<ProductEntity> productEntity);
    void updateProduct(ProductEntity productEntity);
    void changeProductStatus(Map<Long, Boolean> productStatus);
    void deleteProduct(Set<Long> productIds);

    ProductEntity findProductById(Long productId);

    Pagination<ProductEntity> findAllProducts(String productName, SearchQuery searchQuery);
    Pagination<ProductEntity> findProductByCategory(String category, SearchQuery searchQuery);
    Pagination<ProductEntity> findProductByBrand(String brand, SearchQuery searchQuery);

}
