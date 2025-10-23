package br.app.fsantana.marketspaceapi.infra.configs.storage.local;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by felip on 20/10/2025.
 */
@Getter
@Setter
@Component
@ConfigurationProperties("api.storage.local-storage")
@Profile("local-storage")
@Valid
public class LocalProperties {

    @NotBlank
    private String path;
    @NotBlank
    private String apiUrl;

}
