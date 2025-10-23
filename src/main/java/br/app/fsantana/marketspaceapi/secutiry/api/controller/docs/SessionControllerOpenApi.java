package br.app.fsantana.marketspaceapi.secutiry.api.controller.docs;

import br.app.fsantana.marketspaceapi.secutiry.api.request.AuthRequest;
import br.app.fsantana.marketspaceapi.secutiry.api.request.RefreshTokenRequest;
import br.app.fsantana.marketspaceapi.secutiry.api.request.UserCreateRequest;
import br.app.fsantana.marketspaceapi.secutiry.api.response.AuthResponse;
import br.app.fsantana.marketspaceapi.secutiry.api.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by felip on 20/10/2025.
 */

@Tag(name = "Session")
@ApiResponse(responseCode = "500", description = "Server error",
        content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
public interface SessionControllerOpenApi {

    @Operation(summary = "Create a user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    ResponseEntity<?> createUser(@RequestBody UserCreateRequest request);

    @Operation(summary = "Login with password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication data"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    ResponseEntity<AuthResponse> authentication(@RequestBody AuthRequest request);

    @Operation(summary = "Generate new Token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New token"),
            @ApiResponse(responseCode = "400", description = "Client bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) ;
}
