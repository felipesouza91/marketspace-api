package br.app.fsantana.marketspaceapi.domain.services;

import br.app.fsantana.marketspaceapi.domain.models.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Created by felip on 18/10/2025.
 */

public interface ProductImageService {

    List<ProductImage> saveAll(UUID productId, List<MultipartFile> files);

}
