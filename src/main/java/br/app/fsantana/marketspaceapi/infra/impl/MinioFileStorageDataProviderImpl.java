package br.app.fsantana.marketspaceapi.infra.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.StorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppFileException;
import br.app.fsantana.marketspaceapi.infra.configs.storage.minio.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by felip on 17/10/2025.
 */

@Log4j2
@Service
@Profile("minio")
@RequiredArgsConstructor
public class MinioFileStorageDataProviderImpl implements StorageDataProvider {

    private final MinioClient minioClient;
    private final MinioProperties properties;

    @Override
    public String uploadFile(String path, String fileName, InputStream inputStream, String contentType) {
        String finalFile = getNameWithPath(path, fileName);
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getBucketName()).build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(properties.getBucketName()).object(finalFile).stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());
            return getFileUrl(path, fileName );
        } catch (Exception e) {
            log.error(e);
            throw new AppFileException("Erro during upload");
        }
    }

    @Override
    public boolean fileExits(String path, String fileName) {
        try {
            minioClient.getObject(GetObjectArgs.builder()
                    .bucket(properties.getBucketName())
                    .object(getNameWithPath(path, fileName)).build());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public String getFileUrl(String path, String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(properties.getBucketName())
                    .object(getNameWithPath(path, fileName))
                            .method(Method.GET)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String path, String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(properties.getBucketName())
                    .object(getNameWithPath(path, fileName))
                    .build());
        } catch (Exception e) {
            throw new AppFileException("Erro during upload");
        }

    }

    private String getNameWithPath(String path, String fileName) {
        return path+ "/"+ fileName;
    }
}
