package br.app.fsantana.marketspaceapi.domain.dataprovider;

import br.app.fsantana.marketspaceapi.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public interface ProductDataProvider extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdAndUserId(UUID id, UUID id1);

    List<Product> findByUserId(UUID id);
}
