package br.app.fsantana.marketspaceapi.secutiry.services;

import br.app.fsantana.marketspaceapi.domain.models.User;

/**
 * Created by felip on 13/10/2025.
 */

public interface UserSessionService {

    User getCurrentUser();
}
