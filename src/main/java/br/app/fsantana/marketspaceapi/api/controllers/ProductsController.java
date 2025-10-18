package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.api.requests.DeleteImageRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductActiveUpdateRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductFilterRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductCreateRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductUpdateRequest;
import br.app.fsantana.marketspaceapi.api.responses.ProductResponse;
import br.app.fsantana.marketspaceapi.api.responses.ProductResumeResponse;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.services.ProductService;
import br.app.fsantana.marketspaceapi.utils.exceptions.AppException;
import br.app.fsantana.marketspaceapi.utils.mappers.ProductMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "security_auth")
public class ProductsController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductCreateRequest input) {
        Product dataModel = productMapper.toModel(input);
        Product save = productService.save(dataModel);
        ProductResponse result = productMapper.toResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) {
       Product product =  productService.findById(id);
       return ResponseEntity.ok(productMapper.toResponse(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResumeResponse>> getAll(ProductFilterRequest requestFilter) {
      List<Product> result =  productService.findByFilters(requestFilter);
      return ResponseEntity.ok(result.stream().map(productMapper::toResponseResume).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateById(@PathVariable UUID id, @RequestBody ProductUpdateRequest request) {
        Product updated = productService.updateById(id, productMapper.toModel(request));
        return ResponseEntity.ok(productMapper.toResponse(updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> patchById(@PathVariable UUID id, @RequestBody ProductActiveUpdateRequest request) {
        Product updated = productService.changeActiveState(id, request.getIsActive());
        return ResponseEntity.ok(productMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/image/${id}")
    public ResponseEntity<?> saveImage(@PathVariable UUID productId ) {
        throw new AppException("Not implemented");
    }

    @DeleteMapping("/image")
    public ResponseEntity<?> saveImage(@PathVariable DeleteImageRequest request ) {
        throw new AppException("Not implemented");
    }
}
