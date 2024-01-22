package br.com.stoom.resources.docs;

import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateBrandRequestSet;
import br.com.stoom.dto.request.UpdateBrandRequest;
import br.com.stoom.dto.response.FindAllBrandResponse;
import br.com.stoom.dto.response.FindBrandByIdResponse;
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

@Tag(name = "brands")
public interface BrandAPI {

    @Operation(summary = "create a new brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> createBrands(@RequestBody CreateBrandRequestSet createBrandsRequest);

    @Operation(summary = "find brand by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<FindBrandByIdResponse> findBrandById(@PathVariable Long brandId);

    @Operation(
            summary = "remove brand by id",
            description =
                    """
                        example:
                        api/brands/v1?brandIds=4,5
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand removed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> removeBrandById(@RequestParam Set<Long> brandIds);

    @Operation(summary = "change brand status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand status changed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> changeStatusBrand(@RequestBody ChangeStatusRequestSet changeStatusRequest);

    @Operation(summary = "update brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> updateBrand(@RequestBody UpdateBrandRequest updateBrandRequest);

    @Operation(summary = "find all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brands found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Pagination<FindAllBrandResponse>> findAllBrands(

            @RequestParam(name = "brandName", required = false, defaultValue = "") String brandName,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") String direction
    );

}
