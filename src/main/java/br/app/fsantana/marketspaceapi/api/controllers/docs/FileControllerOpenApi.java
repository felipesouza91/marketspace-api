package br.app.fsantana.marketspaceapi.api.controllers.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by felip on 21/10/2025.
 */

@Tag(name = "Files")
@SecurityRequirement(name = "security_auth")
@Profile("local-storage")
public interface FileControllerOpenApi {


    @Operation(summary = "Load file by bame")
    @ApiResponse(responseCode = "200", description = "File data", content = @Content(mediaType = ""))
    @ApiResponse(responseCode = "404", description = " File not found",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Server error",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<Object> getImage(@PathVariable String fileName);

}
