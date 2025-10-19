package br.app.fsantana.marketspaceapi.secutiry.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by felip on 18/10/2025.
 */

@Getter
@Setter
@Component
@ConfigurationProperties("api.security")
@Valid
public class SecurityProperties {

    private final TokenProperties token = new TokenProperties();

    @Getter
    @Setter
    public class TokenProperties {

        @NotBlank
        private String secret;
        @NotBlank
        private Integer expirationTime;
    }
}
