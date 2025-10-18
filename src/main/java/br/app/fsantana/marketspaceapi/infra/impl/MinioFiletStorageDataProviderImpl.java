package br.app.fsantana.marketspaceapi.infra.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.FileStorageDataProvider;
import br.app.fsantana.marketspaceapi.utils.exceptions.AppFileException;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by felip on 17/10/2025.
 */

@Service
@Profile("minio")
@RequiredArgsConstructor
public class MinioFiletStorageDataProviderImpl implements FileStorageDataProvider {

    private final MinioClient minioClient;

    @Override
    public String uploadFile(String bucketName, String fileName, InputStream inputStream, String contentType) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());
            return "";
        } catch (Exception e) {
            throw new AppFileException("Erro during upload");
        }
    }

    @Override
    public boolean fileExits(String bucketName, String fileName) {
        try {
            minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName).build());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public String getFileUrl(String bucketName, String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                            .method(Method.GET)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
