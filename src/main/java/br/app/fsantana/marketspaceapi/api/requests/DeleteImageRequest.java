package br.app.fsantana.marketspaceapi.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

/**
 * Created by felip on 17/10/2025.
 */

@Getter
@Setter
public class DeleteImageRequest {

    @NotNull
    private Set<UUID> imagesIds;
}
