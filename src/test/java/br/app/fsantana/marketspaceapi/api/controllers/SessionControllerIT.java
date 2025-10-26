package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.configs.TestIntegrationConfig;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.api.request.AuthRequest;
import br.app.fsantana.marketspaceapi.secutiry.api.request.RefreshTokenRequest;
import br.app.fsantana.marketspaceapi.secutiry.api.request.UserCreateRequest;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by felip on 20/10/2025.
 */
public class SessionControllerIT  extends TestIntegrationConfig {

    @Test
    @DisplayName("should return 201 when create a user")
    public void test0() throws Exception {
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

    @Test
    @DisplayName("should return 200 when create create a token")
    public void test1() throws Exception {

        User user = createUser();

        AuthRequest body = AuthRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/token")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("should return 200 when create create a token with refreshToken")
    public void test2() throws Exception {

        Auth token = token();

        RefreshTokenRequest body = RefreshTokenRequest.builder()
                .refreshToken(UUID.fromString(token.getRefreshToken())).build();


        given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/refresh-token")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("should return 400 when create with user already exits")
    public void test3() throws Exception {

        User user = createUser();

        UserCreateRequest body = UserCreateRequest.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .tel(user.getTel())
                .build();

        given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(400)
                .body("detail", is("Invalid data try again with new datas"));
    }

    @Test
    @DisplayName("should return 400 when create with tel already exits")
    public void test4() throws Exception {

        User user = createUser();

        UserCreateRequest body = UserCreateRequest.builder()
                .name(user.getName())
                .email("johndoe2@email.com")
                .password(user.getPassword())
                .tel(user.getTel())
                .build();

        given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(400)
                .body("detail", is("Invalid data try again with new datas"));
    }
}
