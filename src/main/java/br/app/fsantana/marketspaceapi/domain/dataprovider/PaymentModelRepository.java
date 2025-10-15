package br.app.fsantana.marketspaceapi.domain.dataprovider;

import br.app.fsantana.marketspaceapi.domain.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public interface PaymentModelRepository extends JpaRepository<PaymentMethod, UUID> {
    Optional<PaymentMethod> findByKey(String key);
}
