package br.app.fsantana.marketspaceapi.secutiry.services;

import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;

import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public interface SessionService  {
    User createUser(User user);

    Auth auth(String email, String password);

    Auth refreshToken(UUID refreshToken);

    Auth createToken(User user);
}
