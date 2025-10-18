package br.app.fsantana.marketspaceapi.infra.configs;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by felip on 17/10/2025.
 */
@Configuration
@Profile("minio")
public class MinioConfig {

    @Value("${api.storage.minio.url}")
    private String url;

    @Value("${api.storage.minio.accessKey}")
    private String accessKey;

    @Value("${api.storage.minio.accessSecret}")
    private String accessSecret;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, accessSecret)
                .build();
    }
}
