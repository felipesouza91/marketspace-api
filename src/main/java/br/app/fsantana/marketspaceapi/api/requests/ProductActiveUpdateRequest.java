package br.app.fsantana.marketspaceapi.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class ProductActiveUpdateRequest {

    @NotNull
    private Boolean isActive;
}
