package br.app.fsantana.marketspaceapi.utils.mappers;

import br.app.fsantana.marketspaceapi.api.responses.ProductImageResponse;
import br.app.fsantana.marketspaceapi.domain.models.ProductImage;
import org.mapstruct.Mapper;

/**
 * Created by felip on 19/10/2025.
 */

@Mapper
public interface ProductImageMapper {


    ProductImageResponse toResponseImage(ProductImage image);
}
