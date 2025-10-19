package br.app.fsantana.marketspaceapi.domain.dataprovider;

import br.app.fsantana.marketspaceapi.domain.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by felip on 18/10/2025.
 */

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
}
