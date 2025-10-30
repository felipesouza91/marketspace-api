package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.api.controllers.docs.MeControllerOpenApi;
import br.app.fsantana.marketspaceapi.api.responses.FileResponse;
import br.app.fsantana.marketspaceapi.api.responses.MeProductResponse;
import br.app.fsantana.marketspaceapi.api.responses.MeResponse;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.MeService;
import br.app.fsantana.marketspaceapi.domain.services.UserFileService;
import br.app.fsantana.marketspaceapi.utils.mappers.FileMapper;
import br.app.fsantana.marketspaceapi.utils.mappers.UserMapper;
import br.app.fsantana.marketspaceapi.utils.validations.FileSize;
import br.app.fsantana.marketspaceapi.utils.validations.FileType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by felip on 15/10/2025.
 */

@RestController
@RequestMapping(path = "/me")
@RequiredArgsConstructor
public class MeController implements MeControllerOpenApi  {

    private final MeService meService;
    private final UserMapper userMapper;
    private final FileMapper fileMapper;
    private final UserFileService userFileService;

    @GetMapping
    public ResponseEntity<MeResponse> getMyInfo() {
        User user = meService.findInfo();
        return ResponseEntity.ok(userMapper.toMeResponse(user));
    }

    @GetMapping("/products")
    public ResponseEntity<Set<MeProductResponse>> getProducts() {
        Set<Product> products = meService.findMyProducts();
        Set<MeProductResponse> results = products.stream()
                .map(userMapper::toMeProductResponse).collect(Collectors.toSet());
        return ResponseEntity.ok(results);
    }


    @PostMapping(value = "/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> uploadAvatar(@Valid @NotNull @FileSize(max = "1MB") @FileType(types = {"png", "jpeg", "jpg"}) MultipartFile file) {
        var data = userFileService.uploadFile(file);
        return  ResponseEntity.ok(fileMapper.toResponse(data));
    }

}
