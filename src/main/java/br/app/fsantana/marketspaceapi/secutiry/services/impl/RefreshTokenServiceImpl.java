package br.app.fsantana.marketspaceapi.secutiry.services.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.RefreshTokenDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.RefreshToken;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.RefreshTokenService;
import br.app.fsantana.marketspaceapi.utils.exceptions.AppEntityNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenDataProvider refreshTokenDataProvider;
    private final UserDataProvider userDataProvider;

    @Override
    public RefreshToken generate(User user) {
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setUser(user);
        newRefreshToken.setExpiresIn(OffsetDateTime.now().plusDays(2).toEpochSecond());
        user.setRefreshToken(null);
        return refreshTokenDataProvider.save(newRefreshToken);
    }

    @Override
    public RefreshToken findById(UUID id) {
        UUID userId = UUID.randomUUID();
        return refreshTokenDataProvider.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new AppEntityNotFound("Refresh token not found"));

    }
}
