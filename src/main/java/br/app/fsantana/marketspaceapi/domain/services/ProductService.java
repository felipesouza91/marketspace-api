package br.app.fsantana.marketspaceapi.domain.services;

import br.app.fsantana.marketspaceapi.api.requests.ProductFilterRequest;
import br.app.fsantana.marketspaceapi.domain.models.Product;

import java.util.List;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public interface ProductService {

    Product save(Product product);

    Product findById(UUID id);

    List<Product> findByFilters(ProductFilterRequest requestFilter);

    Product updateById(UUID id, Product model);

    Product changeActiveState(UUID id, Boolean isActive);

    void deleteById(UUID id);
}
