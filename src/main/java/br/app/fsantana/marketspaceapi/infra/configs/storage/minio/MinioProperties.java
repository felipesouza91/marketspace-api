package br.app.fsantana.marketspaceapi.infra.configs.storage.minio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@Valid
public class MinioProperties {

    @NotBlank
    private String bucketName;
    @NotBlank
    private String url;
    @NotBlank
    private String accessKey;
    @NotBlank
    private String accessSecret;

}
