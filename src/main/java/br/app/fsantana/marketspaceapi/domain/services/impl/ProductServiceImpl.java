package br.app.fsantana.marketspaceapi.domain.services.impl;

import br.app.fsantana.marketspaceapi.api.requests.ProductFilterRequest;
import br.app.fsantana.marketspaceapi.domain.dataprovider.StorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.PaymentMethodRepository;
import br.app.fsantana.marketspaceapi.domain.dataprovider.ProductDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.specifications.ProductSpecs;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppEntityNotFound;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppRuleException;
import br.app.fsantana.marketspaceapi.domain.models.PaymentMethod;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.ProductService;
import br.app.fsantana.marketspaceapi.secutiry.services.UserSessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by felip on 12/10/2025.
 */

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService  {

    private final ProductDataProvider repository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserSessionService userSessionService;
    private final StorageDataProvider storageDataProvider;

    @Override
    public Product save(Product product) {

        Set<PaymentMethod> payments = product.getPaymentMethods()
                .stream()
                .map(paymentMethod -> paymentMethodRepository
                        .findByKey(paymentMethod.getKey())
                        .orElseThrow(() -> new AppRuleException("PaymentMethod invalid")) )
                .collect(Collectors.toSet());

        product.setPaymentMethods(payments);
        product.setUser(getUser());

        return repository.save(product);
    }

    @Override
    public Product findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new AppEntityNotFound("Product not found"));
    }

    @Override
    public List<Product> findByFilters(ProductFilterRequest requestFilter) {
       return repository.findAll(ProductSpecs.withFilter(getUser().getId(), requestFilter));
    }

    @Override
    public Product updateById(UUID id, Product model) {

        Set<PaymentMethod> newsPayments = model.getPaymentMethods()
                .stream().map(paymentMethod -> paymentMethodRepository.findByKey(paymentMethod.getKey())
                .orElseThrow(() -> new AppRuleException("PaymentMethod invalid"))).collect(Collectors.toSet());

        Product productSaved = repository.findByIdAndUserId(id, getUser().getId())
                .orElseThrow(() -> new AppEntityNotFound("Product not found"));

        productSaved.setName(model.getName());
        productSaved.setDescription(model.getDescription());
        productSaved.setIsNew(model.getIsNew());
        productSaved.setPrice(model.getPrice());
        productSaved.setAcceptTrade(model.getAcceptTrade());
        productSaved.setPaymentMethods(newsPayments);
        productSaved.setUpdatedAt(OffsetDateTime.now());

        return repository.save(productSaved);
    }

    @Override
    public Product changeActiveState(UUID id, Boolean isActive) {
        Product productSaved = repository.findByIdAndUserId(id, getUser().getId())
                .orElseThrow(() -> new AppEntityNotFound("Product not found"));

        productSaved.setIsActive(isActive);
        productSaved.setUpdatedAt(OffsetDateTime.now());

        return repository.save(productSaved);
    }

    @Override
    public void deleteById(UUID id) {
        Product product = repository.findByIdAndUserId(id, getUser().getId())
                .orElseThrow(() -> new AppEntityNotFound("Product not found"));

        product.getProductImages()
                .forEach(item -> {
                    String substring = item.getContentType().substring(item.getContentType().indexOf("/") + 1);
                    storageDataProvider.deleteFile(item.getPath(), item.getId().toString() + "."+ substring);
                });

        repository.deleteById(id);
    }

    private User getUser() {
        return userSessionService.getCurrentUser();
    }
}
