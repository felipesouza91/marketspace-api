package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.api.controllers.docs.FileControllerOpenApi;
import br.app.fsantana.marketspaceapi.domain.dataprovider.FileRepository;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppEntityNotFound;
import br.app.fsantana.marketspaceapi.domain.models.File;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.LocalProperties;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.LocalStorageService;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.impl.response.FileResponseData;
import br.app.fsantana.marketspaceapi.infra.dataproviders.LocalStorageDataProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.activation.FileDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Created by felip on 21/10/2025.
 */

@RestController
@RequestMapping("/files")
@Profile("local-storage")
@AllArgsConstructor
public class FileController implements FileControllerOpenApi {

    private final LocalStorageService localStorageService;

    @GetMapping("{fileName}" )
    public ResponseEntity<?> getImage(@PathVariable String fileName) {

        FileResponseData fileResponseData = localStorageService.loadFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileResponseData.getContentType()));

        return new ResponseEntity<>(fileResponseData.getContent(), headers, HttpStatus.OK);
    }


}
