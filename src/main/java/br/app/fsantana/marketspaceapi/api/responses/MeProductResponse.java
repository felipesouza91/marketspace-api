package br.app.fsantana.marketspaceapi.api.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by felip on 15/10/2025.
 */

@Getter
@Setter
public class MeProductResponse {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Boolean isNew;
    private Boolean acceptTrade;
    private List<ProductImageResumeResponse> images;
    private List<PaymentResumeResponse> paymentMethods;
}
