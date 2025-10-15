package br.app.fsantana.marketspaceapi.secutiry.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class RefreshTokenRequest {

    private UUID refreshToken;
}
