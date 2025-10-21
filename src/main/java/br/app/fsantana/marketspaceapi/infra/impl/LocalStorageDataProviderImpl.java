package br.app.fsantana.marketspaceapi.infra.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.StorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppException;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppFileException;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.LocalProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Created by felip on 20/10/2025.
 */

@Service
@Profile("local-storage")
@RequiredArgsConstructor
public class LocalStorageDataProviderImpl implements StorageDataProvider {

    private final LocalProperties localProperties;

    @Override
    public String uploadFile(String path, String fileName, InputStream inputStream, String contentType) {
        try {
            Path saved = Path.of(localProperties.getPath(), path, fileName);

            Files.copy(inputStream, saved, StandardCopyOption.REPLACE_EXISTING);
            return saved.toString();
        } catch (Exception e) {
            throw new AppException("Erro when update files");
        }
    }

    @Override
    public boolean fileExits(String path, String fileName) {
        Path finalPath = Path.of(localProperties.getPath(), path, fileName);

        return Files.exists(finalPath);
    }

    @Override
    public String getFileUrl(String path, String fileName) {
        Path finalPath = Path.of(localProperties.getPath(), path, fileName);
        if (fileExits(path, fileName)) {
            return finalPath.toString();
        }
        throw new AppFileException("File not found");
    }

    @Override
    public void deleteFile(String path, String fileName) {
        Path finalPath = Path.of(localProperties.getPath(), path, fileName);
        try {
            Files.delete(finalPath);
        } catch (IOException e) {
            throw new AppFileException("File not found");
        }

    }
}
