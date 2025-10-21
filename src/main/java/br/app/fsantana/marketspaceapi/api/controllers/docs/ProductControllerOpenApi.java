package br.app.fsantana.marketspaceapi.api.controllers.docs;

import br.app.fsantana.marketspaceapi.api.requests.ProductActiveUpdateRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductCreateRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductFilterRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductUpdateRequest;
import br.app.fsantana.marketspaceapi.api.responses.ProductResponse;
import br.app.fsantana.marketspaceapi.api.responses.ProductResumeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

/**
 * Created by felip on 20/10/2025.
 */


@Tag(name = "Product")
@SecurityRequirement(name = "security_auth")
@ApiResponse(responseCode = "401", description = "Unauthorized",
        content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
@ApiResponse(responseCode = "500", description = "Server error",
        content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
public interface ProductControllerOpenApi {

    @Operation(summary = "Create product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created data"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    ResponseEntity<ProductResponse> create(@RequestBody ProductCreateRequest input);

    @Operation(summary = "Load product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product data"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<ProductResponse> getById(@PathVariable UUID id) ;

    @Operation(summary = "Get products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product images data"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<List<ProductResumeResponse>> getAll(ProductFilterRequest requestFilter);

    @Operation(summary = "Upload product data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated data"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product data not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<ProductResponse> updateById(@PathVariable UUID id, @RequestBody ProductUpdateRequest request);

    @Operation(summary = "Active product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product images data"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product data not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<ProductResponse> patchById(@PathVariable UUID id, @RequestBody ProductActiveUpdateRequest request);

    @Operation(summary = "Delete product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product images data"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product data not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<Void> deleteById(@PathVariable UUID id);

}
