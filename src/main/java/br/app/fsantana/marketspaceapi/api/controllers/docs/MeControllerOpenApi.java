package br.app.fsantana.marketspaceapi.api.controllers.docs;

import br.app.fsantana.marketspaceapi.api.responses.FileResponse;
import br.app.fsantana.marketspaceapi.api.responses.MeProductResponse;
import br.app.fsantana.marketspaceapi.api.responses.MeResponse;
import br.app.fsantana.marketspaceapi.utils.validations.FileSize;
import br.app.fsantana.marketspaceapi.utils.validations.FileType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * Created by felip on 20/10/2025.
 */

@Tag(name = "Me")
@SecurityRequirement(name = "security_auth")
@ApiResponse(responseCode = "401", description = "Unauthorized",
        content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
@ApiResponse(responseCode = "500", description = "Server error",
        content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
public interface MeControllerOpenApi {


    @Operation(summary = "Find user info")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User data"),

            @ApiResponse(responseCode = "404", description = "User data not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<MeResponse> getMyInfo();

    @Operation(summary = "Get user products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Product data"),
            @ApiResponse(responseCode = "404", description = "User Product data not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<Set<MeProductResponse>> getProducts();

    @Operation(summary = "Upload user Avatar")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Upload user Avatar success"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<FileResponse> uploadAvatar(@Valid @NotNull @FileSize(max = "1MB") @FileType(types = {"png", "jpeg", "jpg"})  MultipartFile file) ;
}
