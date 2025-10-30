package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.api.controllers.docs.ProductImageControllerOpenApi;
import br.app.fsantana.marketspaceapi.api.responses.ProductImageResponse;
import br.app.fsantana.marketspaceapi.domain.services.ProductImageService;
import br.app.fsantana.marketspaceapi.utils.mappers.ProductImageMapper;
import br.app.fsantana.marketspaceapi.utils.validations.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by felip on 19/10/2025.
 */

@RestController
@RequestMapping("/products/{productId}")
@RequiredArgsConstructor
public class ProductImagesController implements ProductImageControllerOpenApi {

    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;

    @Override
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Set<ProductImageResponse>> saveImage(
            @PathVariable UUID productId,
            @FileType(types = {"png", "jpeg", "jpg"}) List<MultipartFile> files) {

        Set<ProductImageResponse> collect = productImageService.saveAll(productId, files)
                .stream().map(productImageMapper::toResponseImage)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(collect);
    }

    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<Void> deleteImages(
            @PathVariable UUID productId,  @PathVariable UUID imageId ) {
        productImageService.deleteImage(productId, imageId);
        return ResponseEntity.noContent().build();
    }

}
