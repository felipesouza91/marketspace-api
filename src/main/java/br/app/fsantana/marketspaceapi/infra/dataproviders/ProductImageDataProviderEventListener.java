package br.app.fsantana.marketspaceapi.infra.dataproviders;

import br.app.fsantana.marketspaceapi.domain.dataprovider.FileStorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.ProductImage;
import jakarta.persistence.PostLoad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by felip on 19/10/2025.
 */

@Service
@RequiredArgsConstructor
public class ProductImageDataProviderEventListener {

    private final FileStorageDataProvider fileStorageDataProvider;

    @PostLoad
    public void setImageUrl(ProductImage product) {
        String url = fileStorageDataProvider.getFileUrl(product.getPath(), product.getId().toString());
        product.setImageUrl(url);
    }
}
