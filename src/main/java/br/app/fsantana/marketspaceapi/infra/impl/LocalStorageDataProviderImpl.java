package br.app.fsantana.marketspaceapi.infra.impl;

import br.app.fsantana.marketspaceapi.domain.exceptions.AppException;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppFileException;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.LocalProperties;
import br.app.fsantana.marketspaceapi.infra.dataproviders.LocalStorageDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by felip on 20/10/2025.
 */

@Service
@Profile("local-storage")
@RequiredArgsConstructor
public class LocalStorageDataProviderImpl implements LocalStorageDataProvider {

    private final LocalProperties localProperties;

    @Override
    public String uploadFile(String path, String fileName, InputStream inputStream, String contentType) {
        try {
            Path uploadPath = Paths.get(localProperties.getPath(), path);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path saved = Path.of(localProperties.getPath(), path, fileName);

            Files.copy(inputStream, saved, StandardCopyOption.REPLACE_EXISTING);
            return saved.toString();
        } catch (Exception e) {
            throw new AppException("Erro when update files",e);
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
            return String.format("%s/files/%s",localProperties.getApiUrl(), fileName);
        }
        throw new AppFileException("File not found");
    }

    @Override
    public void deleteFile(String path, String fileName) {
        Path finalPath = Path.of(localProperties.getPath(), path, fileName);
        try {
            Files.delete(finalPath);
        } catch (IOException e) {
            throw new AppFileException("File not found", e );
        }

    }

    @Override
    public byte[] loadImage(String path) {
        try {
            return Files.readAllBytes(Path.of(localProperties.getPath(), path));
        } catch (IOException e) {
            throw new AppFileException("Load file error",e);
        }
    }
}
