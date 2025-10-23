package br.app.fsantana.marketspaceapi.domain.services.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.FileRepository;
import br.app.fsantana.marketspaceapi.domain.dataprovider.StorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppException;
import br.app.fsantana.marketspaceapi.domain.models.File;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.UserFileService;
import br.app.fsantana.marketspaceapi.secutiry.services.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by felip on 17/10/2025.
 */

@Service
@RequiredArgsConstructor
public class UserFileServiceImpl implements UserFileService {

    private final StorageDataProvider storageDataProvider;
    private final UserDataProvider userDataProvider;
    private final UserSessionService userSessionService;
    private final FileRepository fileRepository;

    @Override
    public String uploadFile( MultipartFile file) {
        try {
            Path filePath = storageLocal(file);


            String content = file.getContentType().substring(file.getContentType().indexOf("/")+1);
            String avatarName = getCurrentUser().getId().toString() + "."+content;
            File avatarFile = File.builder()
                    .path("avatars")
                    .fileName(avatarName)
                    .originalFileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .build();

            File save = fileRepository.save(avatarFile);

            String url = storageDataProvider.uploadFile("avatars", avatarName, file.getInputStream(), file.getContentType());

            getCurrentUser().setAvatar(save);
            userDataProvider.save(getCurrentUser());

            Files.delete(filePath);

            return url;
        } catch (IOException e) {
            throw new AppException("Erro when update files");
        }
    }


    private Path storageLocal(MultipartFile file) {
        try {
            Path uploadPath = Paths.get("uploads/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        } catch (Exception e) {
            throw new AppException("Erro when update files");
        }
    }

    private User getCurrentUser() {
        return userSessionService.getCurrentUser();
    }
}
