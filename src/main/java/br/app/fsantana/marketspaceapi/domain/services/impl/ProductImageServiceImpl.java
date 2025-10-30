package br.app.fsantana.marketspaceapi.domain.services.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.FileRepository;
import br.app.fsantana.marketspaceapi.domain.dataprovider.ProductDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.StorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppEntityNotFound;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppException;
import br.app.fsantana.marketspaceapi.domain.models.File;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.ProductImageService;
import br.app.fsantana.marketspaceapi.secutiry.services.UserSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by felip on 18/10/2025.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final StorageDataProvider storageDataProvider;
    private final ProductDataProvider productDataProvider;
    private final UserSessionService userSessionService;
    private final FileRepository fileRepository;

    @Override
    public Set<File> saveAll(UUID productId, List<MultipartFile> files) {
        Product product = productDataProvider.findByIdAndUserId(productId, getCurrentUser().getId())
                .orElseThrow(() -> new AppEntityNotFound("Product not found"));
        Set<File> images = files.stream().map(item -> saveImage(product, item)).collect(Collectors.toSet());

        product.getProductImages().addAll(images);
        productDataProvider.save(product);
        return images;
    }

    @Override
    public void deleteImage(UUID productId, UUID imageId) {
        File productImage = productDataProvider
                .findFileByProductAndUserId(imageId, productId, getCurrentUser().getId())
                .orElseThrow(() -> new AppEntityNotFound("Product Image not found"));

        storageDataProvider.deleteFile(productImage.getPath(), productImage.getFileName() );
        fileRepository.deleteById(imageId);
    }


    private File saveImage(Product product, MultipartFile file) {
        String updatePath = Path.of("products", product.getId().toString()).toString();

        try {
            String content = file.getContentType().substring(file.getContentType().indexOf("/")+1);

            File newFile = File.builder()
                    .path(updatePath)
                    .fileName(UUID.randomUUID().toString()+ "." + content )
                    .originalFileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .build();

            fileRepository.save(newFile);
            String url = storageDataProvider.uploadFile(updatePath, newFile.getFileName(), file.getInputStream(), file.getContentType());
            newFile.setImageUrl(url);
            return newFile;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException("Error when update files", e);
        }
    }

    private User getCurrentUser() {
        return userSessionService.getCurrentUser();
    }
}
