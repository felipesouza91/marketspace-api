package br.app.fsantana.marketspaceapi.domain.dataprovider.specifications;

import br.app.fsantana.marketspaceapi.api.requests.ProductFilterRequest;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

public class ProductSpecs {

    public static Specification<Product> withFilter(UUID userId, ProductFilterRequest filters) {
        return (root, query, builder) -> {
            Predicate[] predicates = withFilterPredicates(root, builder, filters,  userId);

            return predicates.length > 0 ? builder.and(predicates) : null;
        };
    }

    private static Predicate[] withFilterPredicates(Root<Product> root,
                                              CriteriaBuilder criteriaBuilder, ProductFilterRequest filters, UUID userId) {

        List<Predicate> likePredicates = new ArrayList<>();
        likePredicates.add(criteriaBuilder.equal(root.get("isActive"), true));
        likePredicates.add(criteriaBuilder.notEqual(root.get("user").get("id"), userId));
        if( Objects.isNull(filters)) {
            return likePredicates.toArray(Predicate[]::new);
        }
        if (Objects.isNull(filters.getIsNew())) {
            likePredicates.add(criteriaBuilder.equal(root.get("isNew"), true));
        } else {
            likePredicates.add(criteriaBuilder.equal(root.get("isNew"), filters.getIsNew()));
        }
        if (Objects.isNull(filters.getAcceptTrade())) {
            likePredicates.add(criteriaBuilder.equal(root.get("acceptTrade"), true));
        } else {
            likePredicates.add(criteriaBuilder.equal(root.get("acceptTrade"), filters.getAcceptTrade()));
        }
        if (!Objects.isNull(filters.getPaymentMethods())) {
            likePredicates.add(root.get("paymentMethods").get("key").in(filters.getPaymentMethods()));
        }
        if (!Objects.isNull(filters.getQuery())) {
            likePredicates.add(root.get("name").in(filters.getQuery()));
        }
        return likePredicates.toArray(Predicate[]::new);
    }
}
