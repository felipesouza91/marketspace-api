package br.app.fsantana.marketspaceapi.domain.services.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.FileStorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.ProductDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.ProductImageRepository;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppEntityNotFound;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppException;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.ProductImage;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.ProductImageService;
import br.app.fsantana.marketspaceapi.secutiry.services.UserSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final FileStorageDataProvider fileStorageDataProvider;
    private final ProductDataProvider productDataProvider;
    private final UserSessionService userSessionService;
    private final ProductImageRepository productImageRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductImage> saveAll(UUID productId, List<MultipartFile> files) {
        try {
            Product product = productDataProvider.findByIdAndUserId(productId, getCurrentUser().getId())
                    .orElseThrow(() -> new AppEntityNotFound("Product not found"));

            Set<ProductImage> imagens = files.stream().map(item -> saveImage(product, item)).collect(Collectors.toSet());
            return List.of();
        } catch (Exception e) {
            throw e;
        }

    }


    private ProductImage saveImage(Product product, MultipartFile file) {
        try {
            Path filePath = storageLocal(file);
            ProductImage productImage = new ProductImage();
            String updatePath = "products"+ "/" + product.getId().toString();
            productImage.setPath(updatePath);
            productImage.setProduct(product);
            ProductImage productImage1 = productImageRepository.save(productImage);

            String content = file.getContentType().substring(file.getContentType().indexOf("/")+1);
            String filename = productImage1.getId() + "."+content;
            String url = fileStorageDataProvider.uploadFile(updatePath, filename, file.getInputStream(), file.getContentType());
            productImage1.setImageUrl(url);
            productImage1.setPath(updatePath);
            Files.delete(filePath);

            return productImage1;
        } catch (Exception e) {
            throw new AppException("Error when update files");
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
