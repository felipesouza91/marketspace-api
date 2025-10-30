package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.api.controllers.docs.FileControllerOpenApi;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.LocalStorageService;
import br.app.fsantana.marketspaceapi.infra.configs.storage.local.impl.response.FileResponseData;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> getImage(@PathVariable String fileName) {

        FileResponseData fileResponseData = localStorageService.loadFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileResponseData.getContentType()));

        return new ResponseEntity<>(fileResponseData.getContent(), headers, HttpStatus.OK);
    }


}
