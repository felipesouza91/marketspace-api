package br.app.fsantana.marketspaceapi.integrations;

import br.app.fsantana.marketspaceapi.configs.TestIntegrationConfig;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by felip on 24/10/2025.
 */

public class MeControllerIT extends TestIntegrationConfig  {


    @Test
    @DisplayName("should return 401 when token is invalid")
    public void test0() throws Exception {
        given()
                .when()
                .get("/me")
                .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("should return user data ")
    public void test1() {
        Auth auth = token();

        given()
                .auth().oauth2(auth.getToken())
                .when()
                .get("/me")
                .then()
                .statusCode(200)
                .body("id", is(auth.getUser().getId().toString()))
                .body("name", is(auth.getUser().getName()))
                .body("email", is(auth.getUser().getEmail()))
                .body("tel", is(auth.getUser().getTel()));
    }

    @Test
    @DisplayName("should return user data ")
    public void test2() {
        Auth auth = token();

        given()
                .auth().oauth2(auth.getToken())
                .when()
                .get("/me")
                .then()
                .statusCode(200)
                .body("id", is(auth.getUser().getId().toString()))
                .body("name", is(auth.getUser().getName()))
                .body("email", is(auth.getUser().getEmail()))
                .body("tel", is(auth.getUser().getTel()));
    }

    @Test
    @DisplayName("should return products from a user")
    public void test3() {
        Auth auth = token();
        Product product = createProduct(auth.getUser());

        given()

                .auth().oauth2(auth.getToken())
                .when()
                .get("/me/products")
                .then()
                .statusCode(200)
                .body("id", hasItem(product.getId().toString()))
                .body("name", hasItem(product.getName()))
                .body("price", notNullValue())
                .body("isNew", hasItem(product.getIsNew()))
                .body("acceptTrade", hasItem(product.getAcceptTrade()))
                .body("paymentMethods.key", notNullValue());
        ;
    }
}
