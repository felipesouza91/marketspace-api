package br.app.fsantana.marketspaceapi.secutiry.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
@Builder
public class AuthRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
