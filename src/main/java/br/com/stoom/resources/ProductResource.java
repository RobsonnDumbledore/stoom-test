package br.com.stoom.resources;

import java.util.Set;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import br.com.stoom.mapper.ProductMapper;
import br.com.stoom.resources.docs.ProductAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.stoom.services.product.ProductService;
import br.com.stoom.dto.request.UpdateProductRequest;
import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateProductRequestSet;
import br.com.stoom.dto.response.FindAllProductResponse;
import br.com.stoom.dto.response.FindProductByIdResponse;
import org.springframework.validation.annotation.Validated;
import br.com.stoom.dto.response.FindProductByBrandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.stoom.dto.response.FindProductByCategoryResponse;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/products")
public class ProductResource implements ProductAPI {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @PostMapping("/v1")
    public ResponseEntity<Void> createProducts(@Validated CreateProductRequestSet createProductsRequest) {

        final var product = ProductMapper.toEntity(createProductsRequest);
        productService.createProduct(product);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/v1/{productId}")
    public ResponseEntity<FindProductByIdResponse> findProductById(Long productId) {

        final var product = ProductMapper.toResponse(this.productService.findProductById(productId));

        return ResponseEntity.ok(product);
    }

    @Override
    @Transactional
    @DeleteMapping("/v1")
    public ResponseEntity<Void> removeProductById(Set<Long> productIds) {

        this.productService.deleteProduct(productIds);

        return ResponseEntity.ok().build();
    }

    @Override
    @PatchMapping("/v1")
    public ResponseEntity<Void> changeProductStatus(@Validated ChangeStatusRequestSet changeStatusRequestSet) {

        final var status = ProductMapper.toService(changeStatusRequestSet);

        productService.changeProductStatus(status);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/v1")
    public ResponseEntity<Void> updateProduct(@Validated UpdateProductRequest updateProductRequest) {

        final var product = ProductMapper.toEntity(updateProductRequest);
        productService.updateProduct(product);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/v1/category")
    public ResponseEntity<Pagination<FindProductByCategoryResponse>> findProductByCategory(
            String category, int page, int size, String sort, String direction) {

        final var query = SearchQuery.with(
                page,
                size,
                sort,
                direction
        );

        final var products = ProductMapper.byCategoryResponse(productService.findProductByCategory(category, query));

        return ResponseEntity.ok(products);
    }

    @Override
    @GetMapping("/v1/brand")
    public ResponseEntity<Pagination<FindProductByBrandResponse>> findProductByBrand(
            String brand, int page, int size, String sort, String direction) {

        final var query = SearchQuery.with(
                page,
                size,
                sort,
                direction
        );

        final var products = ProductMapper.byBrandResponse(productService.findProductByBrand(brand, query));

        return ResponseEntity.ok(products);
    }

    @Override
    @GetMapping("/v1")
    public ResponseEntity<Pagination<FindAllProductResponse>> findAllProducts(
            String productName, int page, int size, String sort, String direction) {

        final var query = SearchQuery.with(
                page,
                size,
                sort,
                direction
        );

        final var products = ProductMapper.byAllProductResponse(productService.findAllProducts(productName, query));

        return ResponseEntity.ok(products);
    }

}
