package br.app.fsantana.marketspaceapi.domain.dataprovider;

import br.app.fsantana.marketspaceapi.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public interface UserDataProvider extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByTel(String tel);
}
