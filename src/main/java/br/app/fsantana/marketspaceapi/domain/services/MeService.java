package br.app.fsantana.marketspaceapi.domain.services;

import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;

import java.util.Set;

/**
 * Created by felip on 15/10/2025.
 */

public interface MeService {

    User findInfo();

    Set<Product> findMyProducts();
}
