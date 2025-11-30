package br.app.fsantana.marketspaceapi.api.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class ProductResumeResponse {
    private UUID id;
    private String name;
    private BigDecimal price;
    private Boolean isNew;
    private Boolean acceptTrade;
    private Boolean isActive;
    private List<ProductImageResumeResponse> images;
    private List<PaymentResumeResponse> paymentMethods;
    private String userAvatar;
 }
