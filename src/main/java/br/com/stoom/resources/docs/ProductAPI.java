package br.com.stoom.resources.docs;

import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateProductRequestSet;
import br.com.stoom.dto.request.UpdateProductRequest;
import br.com.stoom.dto.response.FindAllProductResponse;
import br.com.stoom.dto.response.FindProductByBrandResponse;
import br.com.stoom.dto.response.FindProductByCategoryResponse;
import br.com.stoom.dto.response.FindProductByIdResponse;
import br.com.stoom.utils.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Tag(name = "products")
public interface ProductAPI {

    @Operation(summary = "create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> createProducts(@RequestBody CreateProductRequestSet createProductsRequest);

    @Operation(summary = "find product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<FindProductByIdResponse> findProductById(@PathVariable("productId") Long productId);

    @Operation(
            summary = "remove product by id",
            description =
                    """
                        example:
                        api/products/v1?productIds=4,5
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product removed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> removeProductById(@RequestParam Set<Long> productIds);


    @Operation(summary = "change product status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product status changed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> changeProductStatus(@RequestBody ChangeStatusRequestSet changeStatusRequestSet);

    @Operation(summary = "update product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> updateProduct(@RequestBody UpdateProductRequest updateProductRequest);

    @Operation(summary = "find product by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Pagination<FindProductByCategoryResponse>> findProductByCategory(

            @RequestParam(name = "category") String category,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "price") String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") String direction
    );

    @Operation(summary = "find product by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Pagination<FindProductByBrandResponse>> findProductByBrand(

            @RequestParam(name = "brand") String brand,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "price") String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") String direction
    );

    @Operation(summary = "find all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Pagination<FindAllProductResponse>> findAllProducts(

            @RequestParam(name = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "price") String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") String direction
    );

}
