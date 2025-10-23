package br.app.fsantana.marketspaceapi.domain.dataprovider;

import br.app.fsantana.marketspaceapi.domain.models.File;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public interface ProductDataProvider extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdAndUserId(UUID id, UUID userId);

    List<Product> findByUserId(UUID id);

    @Query("select f from Product p join p.productImages f where p.id = :productId and f.id = :imageId and p.user.id = :id")
    Optional<File> findFileByProductAndUserId(UUID imageId, UUID productId, UUID id);
}
