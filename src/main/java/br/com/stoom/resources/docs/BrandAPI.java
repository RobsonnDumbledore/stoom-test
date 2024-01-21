package br.com.stoom.resources.docs;

import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateBrandRequestSet;
import br.com.stoom.dto.request.UpdateBrandRequest;
import br.com.stoom.dto.response.FindBrandByIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

}
