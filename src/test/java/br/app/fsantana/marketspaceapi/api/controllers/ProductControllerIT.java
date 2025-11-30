package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.api.requests.ProductActiveUpdateRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductCreateRequest;
import br.app.fsantana.marketspaceapi.api.requests.ProductUpdateRequest;
import br.app.fsantana.marketspaceapi.configs.TestIntegrationConfig;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import io.restassured.http.ContentType;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.instancio.Select.field;

/**
 * Created by felip on 26/10/2025.
 */

class ProductControllerIT extends TestIntegrationConfig {

    @Test
    @DisplayName("should return 201 when save product")
    void test0() {
        Auth auth = token();
        String paymentMethod = Instancio.gen().oneOf(getPaymentKeys()).get();
        ProductCreateRequest body = Instancio.of(ProductCreateRequest.class)
                .set(field(ProductCreateRequest::getPaymentMethods), Set.of(paymentMethod))
                        .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", is(body.getName()))
                .body("description", is(body.getDescription()))
                .body("isNew", is(body.getIsNew()))
                .body("price", is(body.getPrice().floatValue()))
                .body("acceptTrade", is(body.getAcceptTrade()))
                .body("isActive", is(true))
                .body("acceptTrade", notNullValue())
                .body("updatedAt", notNullValue())
                .body("images", nullValue())
                .body("paymentMethods.key", hasItem(paymentMethod))
                .body("user.id", is(auth.getUser().getId().toString()));
    }

    @Test
    @DisplayName("should return 400 when payment method is invalid")
    void test1() {
        Auth auth = token();
        String paymentMethod = "invalid";
        ProductCreateRequest body = Instancio.of(ProductCreateRequest.class)
                .set(field(ProductCreateRequest::getPaymentMethods), Set.of(paymentMethod))
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .when()
                .post("/products")
                .then()
                .statusCode(400)
                .body("detail", is("PaymentMethod invalid"));
    }

    @Test
    @DisplayName("should return 400 when required fields not provide")
    void test2() {
        Auth auth = token();
        String paymentMethod = Instancio.gen().oneOf(getPaymentKeys()).get();
        ProductCreateRequest body = Instancio.of(ProductCreateRequest.class)
                .set(field(ProductCreateRequest::getPaymentMethods), Set.of(paymentMethod))
                .setBlank(field(ProductCreateRequest::getName))
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .when()
                .post("/products")
                .then()
                .statusCode(400)
                .body("detail", is("Um ou mais campos estão invalidos. Faça o preenchimento correto e tente novamente"))
                .body("objects", notNullValue());
    }

    @Test
    @DisplayName("should return 200 when find product by id")
    void test3() {
        Auth auth = token();
        Product product = super.createProduct(auth.getUser());
        given()
                .auth().oauth2(auth.getToken())
                .get("/products/"+ product.getId().toString())
                .then()
                .statusCode(200)
                .body("id", is(product.getId().toString()))
                .body("name", is(product.getName()))
                .body("description", is(product.getDescription()))
                .body("isNew", is(product.getIsNew()))
                .body("price", is(product.getPrice().floatValue()))
                .body("acceptTrade", is(product.getAcceptTrade()))
                .body("isActive", is(product.getIsActive()))
                .body("acceptTrade", notNullValue())
                .body("updatedAt", notNullValue())
                .body("images.size()", is(0))
                .body("paymentMethods.key", hasItem(product.getPaymentMethods().stream().findFirst().get().getKey()))
                .body("user.id", is(auth.getUser().getId().toString()));
    }

    @Test
    @DisplayName("should return 404 when find product by id not found")
    void test4() {
        Auth auth = token();

        given()
                .auth().oauth2(auth.getToken())
                .get("/products/"+ UUID.randomUUID().toString())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 400 when update product with pending required field")
    void test5() {
        Auth auth = token();
        User user = super.createUserB();
        Product product = createProduct(user);
        String paymentMethod = Instancio.gen().oneOf(getPaymentKeys()).get();
        ProductUpdateRequest body = Instancio.of(ProductUpdateRequest.class)
                .set(field(ProductUpdateRequest::getPaymentMethods), Set.of(paymentMethod))
                .setBlank(field(ProductUpdateRequest::getName))
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .put("/products/"+ product.getId().toString())
                .then()
                .statusCode(400)
                .body("detail", is("Um ou mais campos estão invalidos. Faça o preenchimento correto e tente novamente"))
                .body("objects", notNullValue());
    }

    @Test
    @DisplayName("should return 400 when update product with pending required field")
    void test6() {
        Auth auth = token();
        User user = super.createUserB();
        Product product = createProduct(user);

        String paymentMethod = "invalid";
        ProductUpdateRequest body = Instancio.of(ProductUpdateRequest.class)
                .set(field(ProductUpdateRequest::getPaymentMethods), Set.of(paymentMethod))
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .put("/products/"+ product.getId().toString())
                .then()
                .statusCode(400)
                .body("detail", is("PaymentMethod invalid"));
    }

    @Test
    @DisplayName("should return 400 when update product that not exists")
    void test7() {
        Auth auth = token();
        String paymentMethod = Instancio.gen().oneOf(getPaymentKeys()).get();
        ProductUpdateRequest body = Instancio.of(ProductUpdateRequest.class)
                .set(field(ProductUpdateRequest::getPaymentMethods), Set.of(paymentMethod))
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .put("/products/"+ UUID.randomUUID().toString())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 400 when update product that has a another user")
    void test8() {
        Auth auth = token();
        User user = createUserB();
        Product product  = createProduct(user);
        String paymentMethod = Instancio.gen().oneOf(getPaymentKeys()).get();
        ProductUpdateRequest body = Instancio.of(ProductUpdateRequest.class)
                .set(field(ProductUpdateRequest::getPaymentMethods), Set.of(paymentMethod))
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .put("/products/"+ product.getId())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 200 when update product")
    void test9() {
        Auth auth = token();
        Product product = createProduct(auth.getUser());
        String paymentMethod = Instancio.gen().oneOf(getPaymentKeys()).get();
        ProductUpdateRequest body = Instancio.of(ProductUpdateRequest.class)
                .set(field(ProductUpdateRequest::getPaymentMethods), Set.of(paymentMethod))
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .put("/products/"+ product.getId())
                .then()
                .statusCode(200)
                .body("id", is(product.getId().toString()))
                .body("name", is(body.getName()))
                .body("description", is(body.getDescription()))
                .body("isNew", is(body.getIsNew()))
                .body("price", is(body.getPrice().floatValue()))
                .body("acceptTrade", is(body.getAcceptTrade()))
                .body("isActive", is(product.getIsActive()))
                .body("acceptTrade", is(body.getAcceptTrade()))
                .body("updatedAt", notNullValue())
                .body("images.size()", is(0))
                .body("paymentMethods.key", hasItem(paymentMethod))
                .body("user.id", is(auth.getUser().getId().toString()));
    }

    @Test
    @DisplayName("should return 400 when change status of a product when required values not provide")
    void test10() {
        Auth auth = token();
        User user = super.createUserB();
        Product product = createProduct(user);
        ProductActiveUpdateRequest body = new ProductActiveUpdateRequest();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .when().patch("/products/"+ product.getId())
                .then()
                .statusCode(400)
                .body("detail", is("Um ou mais campos estão invalidos. Faça o preenchimento correto e tente novamente"))
                .body("objects", notNullValue());
    }

    @Test
    @DisplayName("should return 400 when change status of a product that not exists")
    void test11() {
        Auth auth = token();

        ProductActiveUpdateRequest body = Instancio.create(ProductActiveUpdateRequest.class);
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .when().patch("/products/"+UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 400 when change status of a product that has another user")
    void test12() {
        Auth auth = token();
        User user = createUserB();
        Product product  = createProduct(user);
        ProductActiveUpdateRequest body = Instancio.of(ProductActiveUpdateRequest.class)
                .set(field(ProductActiveUpdateRequest::getIsActive), !product.getIsActive())
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .when().patch("/products/"+product.getId())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 200 when change status of a product")
    void test13() {
        Auth auth = token();
        Product product  = createProduct(auth.getUser());
        ProductActiveUpdateRequest body = Instancio.of(ProductActiveUpdateRequest.class)
                .set(field(ProductActiveUpdateRequest::getIsActive), !product.getIsActive())
                .create();
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .when().patch("/products/"+product.getId())
                .then()
                .statusCode(200)
                .body("isActive", is(body.getIsActive()));
    }

    @Test
    @DisplayName("should return 404 when delete a product that no exists")
    void test14() {
        Auth auth = token();
        given()
                .auth().oauth2(auth.getToken())
                .delete("/products/"+ UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 404 when delete a product that has another user")
    void test15() {
        Auth auth = token();
        User user = createUserB();
        Product product  = createProduct(user);
        given()
                .auth().oauth2(auth.getToken())
                .delete("/products/"+ product.getId())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 204 when delete a product")
    void test16() {
        Auth auth = token();
        Product product  = createProduct(auth.getUser());
        createProductImage(product);
        given()
                .auth().oauth2(auth.getToken())
                .delete("/products/"+ product.getId())
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("should return 200 when find products")
    void test17() {
        Auth auth = token();
        User user = createUserB();
        createProduct(user);
        long totalProducs = countProducts(auth.getUser().getId());
        given()
                .auth().oauth2(auth.getToken())
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", is(Integer.valueOf(String.valueOf(totalProducs))))
                .body("isActive", notNullValue());
        ;
    }
}