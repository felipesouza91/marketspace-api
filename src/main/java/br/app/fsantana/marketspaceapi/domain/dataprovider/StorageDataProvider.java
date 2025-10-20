package br.app.fsantana.marketspaceapi.domain.dataprovider;

import java.io.InputStream;

/**
 * Created by felip on 17/10/2025.
 */

public interface StorageDataProvider {

    String uploadFile(String path, String fileName, InputStream inputStream, String contentType);

    boolean fileExits(String path, String fileName);

    String getFileUrl(String path, String fileName);

    void deleteFile(String path, String fileName);
}
