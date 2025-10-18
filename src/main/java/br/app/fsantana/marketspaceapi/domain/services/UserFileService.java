package br.app.fsantana.marketspaceapi.domain.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.util.UUID;

/**
 * Created by felip on 17/10/2025.
 */

public interface UserFileService {

    String uploadFile( MultipartFile file);

}
