package br.app.fsantana.marketspaceapi.api.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class ProductUpdateRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Boolean isNew;

    @Min(0)
    private BigDecimal price;

    @NotNull
    private Boolean acceptTrade;

    @NotNull
    private Set<String> paymentMethods;
}
