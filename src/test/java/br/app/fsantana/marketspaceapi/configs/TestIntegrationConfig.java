package br.app.fsantana.marketspaceapi.configs;

import br.app.fsantana.marketspaceapi.domain.dataprovider.FileRepository;
import br.app.fsantana.marketspaceapi.domain.dataprovider.PaymentMethodRepository;
import br.app.fsantana.marketspaceapi.domain.dataprovider.ProductDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.StorageDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.dataprovider.specifications.ProductSpecs;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppEntityNotFound;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppException;
import br.app.fsantana.marketspaceapi.domain.models.File;
import br.app.fsantana.marketspaceapi.domain.models.PaymentMethod;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import br.app.fsantana.marketspaceapi.secutiry.services.SessionService;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.instancio.Select.field;

@Testcontainers
@ActiveProfiles({"minio", "test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@TestPropertySource("classpath:application-test.yaml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class TestIntegrationConfig {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
  }

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserDataProvider userDataProvider;

    @Autowired
    private ProductDataProvider productDataProvider;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private StorageDataProvider storageDataProvider;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15").withReuse(true);

    @Container
    static MinIOContainer minioContainer = new MinIOContainer("minio/minio:RELEASE.2025-02-03T21-03-04Z-cpuv1").withReuse(true);

    @DynamicPropertySource
    public static void configProps(DynamicPropertyRegistry registry) {
        registry.add("api.storage.minio.bucketName", () -> "test-bucket");
        registry.add("api.storage.minio.url", minioContainer::getS3URL);
        registry.add("api.storage.minio.accessKey", minioContainer::getUserName);
        registry.add("api.storage.minio.accessSecret", minioContainer::getPassword);
    }

    protected User createUser() {
        String blankPassword=  "12345678";
        User user = new User();
        user.setName("Jonh Doe 1 ");
        user.setEmail("jonhdoe1@email.com");
        user.setPassword(blankPassword);
        user.setTel("559999999");
        Optional<User> byEmail = userDataProvider.findByEmail(user.getEmail());
        if(byEmail.isEmpty()) {
            sessionService.createUser(user);
        }
        user.setPassword(blankPassword);
        return user;
    }

    protected User createUserB() {
        String blankPassword = "12345678";
        User user = new User();
        user.setName("Jonh Doe 2 ");
        user.setEmail("jonhdoe2@email.com");
        user.setPassword(blankPassword);
        user.setTel("559999992");
        user.setPassword(passwordEncoder.encode(blankPassword));
        Optional<User> savedUser = userDataProvider.
                findByEmail(user.getEmail());
        if(savedUser.isPresent()) {
            return savedUser.get();
        }
        userDataProvider.saveAndFlush(user);
        return user;
    }

    protected Auth token() {
        User user = createUser();
        return sessionService.auth(user.getEmail(), user.getPassword());
    }

    @Transactional
    protected Product createProduct(User user) {
        PaymentMethod pix = paymentMethodRepository.findByKey("pix")
                .orElseThrow(() -> new AppEntityNotFound("Entity not found"));
        Product product = Instancio.of(Product.class)
                .setBlank(field(Product::getId))
                .setBlank(field(Product::getUser))
                .setBlank(field(Product::getPaymentMethods))
                .setBlank(field(Product::getUser))
                .set(field(Product::getIsActive), true)
                .setBlank(field(Product::getPaymentMethods))
                .setBlank(field(Product::getProductImages))
                .create();
        product.setUser(user);
        product.getPaymentMethods().add(pix);
        return productDataProvider.saveAndFlush(product);
    }

    protected List<String> getPaymentKeys() {
        return List.of("boleto", "pix", "cash", "card", "deposit");
    }

    protected long countProducts(UUID userId) {
        return productDataProvider.count(ProductSpecs.withFilter(userId, null));
    }

    protected File createProductImage(Product product) {
        try {
            String updatePath = "products"+ "/" + product.getId().toString();
            Path path = filePath();
            java.io.File file = path.toFile();
            String content =  Files.probeContentType(path);

            File newFile = File.builder()
                    .path(updatePath)
                    .fileName(UUID.randomUUID().toString()+ "." + content )
                    .originalFileName(file.getName())
                    .contentType(content)
                    .build();

            fileRepository.save(newFile);
            String url = storageDataProvider.uploadFile(updatePath, newFile.getFileName(), new FileInputStream(file), content);
            newFile.setImageUrl(url);
            product.setProductImages(Set.of(newFile));
            productDataProvider.save(product);
            return newFile;
        } catch (Exception e) {
            throw new AppException("Error when update files", e);
        }
    }

    protected File userAvatar(User user) {
        try {
            Path path = filePath();
            java.io.File fileData = filePath().toFile();


            String content = Files.probeContentType(path);;
            String avatarName = user.getId().toString() + "."+ content.substring(content.indexOf("/")+1);
            File avatarFile = File.builder()
                    .path("avatars")
                    .fileName(avatarName)
                    .originalFileName(fileData.getName())
                    .contentType(content)
                    .build();

            File save = fileRepository.save(avatarFile);

            String url = storageDataProvider.uploadFile("avatars", avatarName, new FileInputStream(fileData), content);

            user.setAvatar(save);
            userDataProvider.save(user);

            Files.delete(path);

            return avatarFile;
        } catch (IOException e) {
            throw new AppException("Erro when update files");
        }
    }

    protected Path filePath() {
        return Path.of("src/test/resources/images/prod1.jpg");
    }

    protected Path largefilePath() {
        return Path.of("src/test/resources/images/large-file.jpg");
    }

}