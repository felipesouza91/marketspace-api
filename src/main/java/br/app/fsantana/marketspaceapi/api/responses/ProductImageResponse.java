package br.app.fsantana.marketspaceapi.api.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created by felip on 19/10/2025.
 */

@Getter
@Setter
public class ProductImageResponse {

    private UUID id;

    private String imageUrl;

    private OffsetDateTime createdAt;

}
