package br.app.fsantana.marketspaceapi.domain.services.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.ProductDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.MeService;
import br.app.fsantana.marketspaceapi.secutiry.services.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by felip on 15/10/2025.
 */

@Service
@RequiredArgsConstructor
public class MeServiceImpl implements MeService {

    private final UserDataProvider userDataProvider;
    private final UserSessionService userSessionService;
    private final ProductDataProvider productDataProvider;

    @Override
    public User findInfo() {
        User currentUser = getUser();
        return userDataProvider.findById(currentUser.getId()).get();
    }

    @Override
    public Set<Product> findMyProducts() {
        List<Product> products = productDataProvider
                .findByUserId(getUser().getId());
        return Set.copyOf(products);
    }

    private User getUser() {
        return userSessionService.getCurrentUser();
    }
}
