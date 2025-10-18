package br.app.fsantana.marketspaceapi.infra.configs.storage.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by felip on 18/10/2025.
 */

@Getter
@Setter
@Component
@ConfigurationProperties("api.storage.minio")
@Profile("minio")
public class MinioProperties {

    private String bucketName;
    private String url;
    private String accessKey;
    private String accessSecret;

}
