package br.app.fsantana.marketspaceapi.domain.services;

import br.app.fsantana.marketspaceapi.domain.models.RefreshToken;
import br.app.fsantana.marketspaceapi.domain.models.User;

import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public interface RefreshTokenService {

    RefreshToken generate(User user);

    RefreshToken findById(UUID id );
}
