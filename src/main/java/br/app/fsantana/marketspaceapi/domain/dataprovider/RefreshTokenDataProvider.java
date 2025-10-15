package br.app.fsantana.marketspaceapi.domain.dataprovider;

import br.app.fsantana.marketspaceapi.domain.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public interface RefreshTokenDataProvider extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByUserId(UUID id);

    Optional<RefreshToken> findByIdAndUserId(UUID id, UUID userId);
}
