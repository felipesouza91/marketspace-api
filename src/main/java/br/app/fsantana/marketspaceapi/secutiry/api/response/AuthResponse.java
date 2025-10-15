package br.app.fsantana.marketspaceapi.secutiry.api.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class AuthResponse {

    private String token;
    private String refreshToken;
}
