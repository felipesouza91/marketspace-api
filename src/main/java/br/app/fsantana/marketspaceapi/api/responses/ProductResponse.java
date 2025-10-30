package br.app.fsantana.marketspaceapi.api.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class ProductResponse {

    private UUID id;
    private String name;
    private String description;
    private Boolean isNew;
    private BigDecimal price;
    private Boolean acceptTrade;
    private Boolean isActive;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Set<ProductImageResumeResponse> images;
    private List<PaymentResumeResponse> paymentMethods;
    private UserResumeResponse user;
}
