package br.app.fsantana.marketspaceapi.utils.mappers;

import br.app.fsantana.marketspaceapi.secutiry.api.response.AuthResponse;
import br.app.fsantana.marketspaceapi.secutiry.api.response.TokenResponse;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import org.mapstruct.Mapper;

/**
 * Created by felip on 12/10/2025.
 */

@Mapper
public interface SessionMapper {

    AuthResponse toResponse(Auth auth);

    TokenResponse toTokenResponse(Auth auth);
}
