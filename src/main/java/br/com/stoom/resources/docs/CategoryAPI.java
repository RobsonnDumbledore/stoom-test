package br.com.stoom.resources.docs;

import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateCategoryRequestSet;
import br.com.stoom.dto.request.UpdateCategoryRequest;
import br.com.stoom.dto.response.FindCategoryByIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "categories")
public interface CategoryAPI {


    @Operation(summary = "create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> createCategories(@RequestBody CreateCategoryRequestSet createCategoriesRequest);

    @Operation(summary = "change category status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category status changed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> changeCategoryStatus(@RequestBody ChangeStatusRequestSet changeStatusRequestSet);

    @Operation(summary = "update category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest);

    @Operation(summary = "find category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<FindCategoryByIdResponse> findCategoryById(@PathVariable("categoryId") Long categoryId);

}
