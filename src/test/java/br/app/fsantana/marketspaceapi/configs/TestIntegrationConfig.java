package br.app.fsantana.marketspaceapi.configs;

import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import br.app.fsantana.marketspaceapi.secutiry.services.SessionService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

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

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15").withReuse(true);


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

    protected Auth token() {
        User user = createUser();
        return sessionService.auth(user.getEmail(), user.getPassword());
    }

}