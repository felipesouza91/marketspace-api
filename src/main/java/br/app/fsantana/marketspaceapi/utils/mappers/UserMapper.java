package br.app.fsantana.marketspaceapi.utils.mappers;

import br.app.fsantana.marketspaceapi.api.responses.MeProductResponse;
import br.app.fsantana.marketspaceapi.api.responses.MeResponse;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.api.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by felip on 12/10/2025.
 */

@Mapper
public interface UserMapper {

    User toModel(UserCreateRequest request);

    MeResponse toMeResponse(User user);

    @Mapping(source = "productImages", target = "images")
    MeProductResponse toMeProductResponse(Product product);
}
