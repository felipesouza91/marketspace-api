package br.app.fsantana.marketspaceapi.domain.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by felip on 17/10/2025.
 */

public interface UserFileService {

    String uploadFile( MultipartFile file);

}
