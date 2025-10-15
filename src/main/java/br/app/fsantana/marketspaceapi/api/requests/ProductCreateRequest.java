package br.app.fsantana.marketspaceapi.api.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class ProductCreateRequest {

   private String name;
   private String description;
   private Boolean isNew;
   private BigDecimal price;
   private Boolean acceptTrade;
   private Set<String> paymentMethods;
}
