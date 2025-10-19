package br.app.fsantana.marketspaceapi.api.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class ProductImageResumeResponse {
    private UUID id;
    private String imageUrl;
}
