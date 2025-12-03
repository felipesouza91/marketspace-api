package br.app.fsantana.marketspaceapi.utils.mappers;

import br.app.fsantana.marketspaceapi.api.requests.ProductCreateRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductUpdateRequest;
import br.app.fsantana.marketspaceapi.api.responses.ProductResponse;
import br.app.fsantana.marketspaceapi.api.responses.ProductResumeResponse;
import br.app.fsantana.marketspaceapi.domain.models.PaymentMethod;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by felip on 12/10/2025.
 */

@Mapper
public interface ProductMapper {

    Product toModel(ProductCreateRequest productRequest);

    Product toModel(ProductUpdateRequest productRequest);

    @Mapping(source = "productImages", target = "images")
    @Mapping(source = "user.avatar.imageUrl", target = "user.avatar")
    ProductResponse toResponse(Product product);

    @Mapping(source = "user.avatar.imageUrl", target = "userAvatar")
    @Mapping(source = "productImages", target = "images")
    ProductResumeResponse toResponseResume(Product product);

    @Mapping(source = "name", target = "key")
    PaymentMethod toModelFromString(String name);
}
