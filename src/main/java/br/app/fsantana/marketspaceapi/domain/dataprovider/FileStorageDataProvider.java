package br.app.fsantana.marketspaceapi.domain.dataprovider;

import java.io.InputStream;
import java.net.URI;

/**
 * Created by felip on 17/10/2025.
 */

public interface FileStorageDataProvider {

    String uploadFile(String bucketName, String fileName, InputStream inputStream, String contentType);

    boolean fileExits(String bucketName, String fileName);

    String getFileUrl(String bucketName, String fileName);
}
