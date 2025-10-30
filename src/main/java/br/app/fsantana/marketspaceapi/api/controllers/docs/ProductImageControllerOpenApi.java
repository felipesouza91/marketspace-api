package br.app.fsantana.marketspaceapi.api.controllers.docs;

import br.app.fsantana.marketspaceapi.api.responses.ProductImageResponse;
import br.app.fsantana.marketspaceapi.utils.validations.FileType;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by felip on 20/10/2025.
 */

@Tag(name = "Product Image")
@SecurityRequirement(name = "security_auth")
@ApiResponse(responseCode = "401", description = "Unauthorized",
        content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
@ApiResponse(responseCode = "500", description = "Server error",
        content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
public interface ProductImageControllerOpenApi {


    @Operation(summary = "Upload product Image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product images data"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product data not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<Set<ProductImageResponse>> saveImage(
            @PathVariable UUID productId,
            @FileType(types = {"png", "jpeg", "jpg"}) List<MultipartFile> files) ;


    @Operation(summary = "Delete product Image")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operation completed"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product/Product Image data not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<Void> deleteImages(
            @PathVariable UUID productId,  @PathVariable UUID imageId ) ;

}
