package br.app.fsantana.marketspaceapi.api.requests;

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

    private Set<UUID> imagesIds;
}
