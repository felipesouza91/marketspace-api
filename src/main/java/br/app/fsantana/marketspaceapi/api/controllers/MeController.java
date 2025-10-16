package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.api.responses.MeProductResponse;
import br.app.fsantana.marketspaceapi.api.responses.MeResponse;
import br.app.fsantana.marketspaceapi.api.responses.ProductResponse;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.MeService;
import br.app.fsantana.marketspaceapi.utils.exceptions.AppException;
import br.app.fsantana.marketspaceapi.utils.mappers.UserMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by felip on 15/10/2025.
 */

@RestController
@RequestMapping(path = "/me")
@SecurityRequirement(name = "security_auth")
@RequiredArgsConstructor
public class MeController {

    private final MeService meService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<MeResponse> getMyInfo() {
        User user = meService.findInfo();
        return ResponseEntity.ok(userMapper.toMeResponse(user));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        Set<Product> products = meService.findMyProducts();
        Set<MeProductResponse> results = products.stream()
                .map(userMapper::toMeProductResponse).collect(Collectors.toSet());
        return ResponseEntity.ok(results);
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar() {
        throw new AppException("Not implemented");
    }

}
