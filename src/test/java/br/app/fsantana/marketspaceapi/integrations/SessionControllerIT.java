package br.app.fsantana.marketspaceapi.integrations;

import br.app.fsantana.marketspaceapi.configs.TestIntegrationConfig;
import br.app.fsantana.marketspaceapi.secutiry.api.request.UserCreateRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
/**
 * Created by felip on 20/10/2025.
 */

public class SessionControllerIT  extends TestIntegrationConfig {

    @Test
    @DisplayName("should return 201 when create a user")
    public void test0() {
        UserCreateRequest body = UserCreateRequest.builder()
                .name("John Doe")
                .email("johndoe@email.com")
                .password("1234567")
                .tel("559999999999")
                .build();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(201);
    }
}
