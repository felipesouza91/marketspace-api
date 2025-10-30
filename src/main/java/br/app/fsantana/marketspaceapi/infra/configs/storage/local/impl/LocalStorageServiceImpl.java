package br.app.fsantana.marketspaceapi.infra.configs.storage.local.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.FileRepository;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppEntityNotFound;
import br.app.fsantana.marketspaceapi.domain.models.File;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.LocalStorageService;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.impl.response.FileResponseData;
import br.app.fsantana.marketspaceapi.infra.dataproviders.LocalStorageDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/**
 * Created by felip on 21/10/2025.
 */

@Service
@Profile("local-storage")
@RequiredArgsConstructor
public class LocalStorageServiceImpl implements LocalStorageService {

    private final LocalStorageDataProvider localStorageDataProvider;
    private final FileRepository fileRepository;

    @Override
    public FileResponseData loadFile(String fileName) {
        File file = fileRepository.findByFileName(fileName).orElseThrow(() -> new AppEntityNotFound("File not found"));

        byte[] bytes = localStorageDataProvider
                .loadImage(Path.of(file.getPath(), file.getFileName()).toString());

        return FileResponseData.builder()
                .content(bytes)
                .contentType(file.getContentType())
                .fileName(file.getFileName())
                .build();
    }
}
