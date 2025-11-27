package br.app.fsantana.marketspaceapi.infra.dataproviders;

import br.app.fsantana.marketspaceapi.domain.dataprovider.StorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.File;
import jakarta.persistence.PostLoad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by felip on 19/10/2025.
 */

@Service
@RequiredArgsConstructor
public class FileDataProviderEventListener {

    private final StorageDataProvider storageDataProvider;

    @PostLoad
    public void setImageUrl(File file) {
        String url = storageDataProvider.getFileUrl(file.getPath(), file.getFileName()).orElse(null);
        file.setImageUrl(url);
    }
}
