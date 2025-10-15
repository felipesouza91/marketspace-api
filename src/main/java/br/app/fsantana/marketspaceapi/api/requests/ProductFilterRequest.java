package br.app.fsantana.marketspaceapi.api.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class ProductFilterRequest {
    private Boolean isNew;
    private Boolean acceptTrade;
    private List<String> query;
    private List<String> paymentMethods;
}
