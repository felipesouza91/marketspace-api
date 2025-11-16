package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.configs.TestIntegrationConfigLocalStorage;
import br.app.fsantana.marketspaceapi.domain.models.File;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

/**
 * Created by felip on 28/10/2025.
 */


class FileControllerIT extends TestIntegrationConfigLocalStorage  {

    @Test
    @DisplayName("should return 404 when upload file of a product that not exists")
    void test0(){
        Auth auth = token();
        given()
                .auth().oauth2(auth.getToken())
                .contentType(ContentType.MULTIPART)
                .multiPart("files", filePath().toFile(), "image/jpg" )
                .post("/products/{productId}/image", UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 404 when upload file of product does not belong to the user")
    void test1() {
        Auth auth = token();
        User user = createUserB();
        Product product = createProduct(user);
        given()
                .auth().oauth2(auth.getToken())
                .contentType(ContentType.MULTIPART)
                .multiPart("files", filePath().toFile(), "image/jpg" )
                .post("/products/{productId}/image", product.getId())
                .then()
                .statusCode(404)
                .body("detail", is("Product not found"));
    }

    @Test
    @DisplayName("should return 400 when upload file with invalid contenty-type")
    void test7(){
        Auth auth = token();
        given()
                .auth().oauth2(auth.getToken())
                .contentType(ContentType.MULTIPART)
                .multiPart("files", filePath().toFile(), "application/pdf" )
                .post("/products/{productId}/image", UUID.randomUUID())
                .then()
                .statusCode(400)
                .body("detail", is("Tipo de arquivo invalido"));
    }

    @Test
    @DisplayName("should return 200 when upload product file with success")
    void test2()  {
        Auth auth = token();
        Product product = createProduct(auth.getUser());
        given()
                .auth().oauth2(auth.getToken())
                .contentType(ContentType.MULTIPART)
                .multiPart("files", filePath().toFile(), "image/jpg" )
                .post("/products/{productId}/image", product.getId())
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("imageUrl[0]", containsString("http"));
    }

    @Test
    @DisplayName("should return 404 when delete product file not exits")
    void test3()  {
        Auth auth = token();
        given()
                .auth().oauth2(auth.getToken())

                .delete("/products/{productId}/image/{imageid}", UUID.randomUUID(), UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("detail", is("Product Image not found"));
    }

    @Test
    @DisplayName("should return 404 when delete product file that belong to another user")
    void test4()  {
        Auth auth = token();
        User user = createUserB();
        Product product = createProduct(user);
        given()
                .auth().oauth2(auth.getToken())
                .delete("/products/{productId}/image/{imageid}", product.getId(), UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("detail", is("Product Image not found"));
    }

    @Test
    @DisplayName("should return 404 when delete product file that belong to another user")
    void test5()  {
        Auth auth = token();
        Product product = createProduct(auth.getUser());
        given()
                .auth().oauth2(auth.getToken())
                .delete("/products/{productId}/image/{imageid}", product.getId(), UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("detail", is("Product Image not found"));
    }

    @Test
    @DisplayName("should return 204 when delete product file success")
    void test6()  {
        Auth auth = token();
        Product product = createProduct(auth.getUser());
        File productImage = createProductImage(product);

        given()
                .auth().oauth2(auth.getToken())
                .delete("/products/{productId}/image/{imageid}", product.getId(), productImage.getId())
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("should return 200 when find by file name")
    void test8()  {
        Auth auth = token();
        Product product = createProduct(auth.getUser());
        File productImage = createProductImage(product);

        given()
                .auth().oauth2(auth.getToken())
                .get("/files/{fileName}", productImage.getFileName())
                .then()
                .statusCode(200)
                .contentType(productImage.getContentType());
    }

    @Test
    @DisplayName("should return 404 when find by file name")
    void test9()  {
        Auth auth = token();
        given()
                .auth().oauth2(auth.getToken())
                .get("/files/{fileName}", "invalid.jped")
                .then()
                .statusCode(404)
                .body("detail", is("File not found"));
    }

    @Test
    @DisplayName("should return 404 when file not found in storage")
    void test10() throws IOException {
        Auth auth = token();
        Product product = createProduct(auth.getUser());
        File productImage = createProductImage(product);
        Path path = fullPath(Path.of(productImage.getPath(), productImage.getFileName()).toString());
        Files.delete(path);
        given()
                .auth().oauth2(auth.getToken())
                .get("/files/{fileName}", productImage.getFileName())
                .then()
                .statusCode(404)
                .body("detail", is("File not found"));
    }

    @Test
    @DisplayName("should return 404 when delete product that not exists in storage")
    void test11() throws IOException {
        Auth auth = token();
        Product product = createProduct(auth.getUser());
        File productImage = createProductImage(product);
        Path path = fullPath(Path.of(productImage.getPath(), productImage.getFileName()).toString());
        Files.delete(path);
        given()
                .auth().oauth2(auth.getToken())
                .delete("/products/{productId}/image/{imageid}", product.getId(), productImage.getId())
                .then()
                .statusCode(404)
                .body("detail", is("File not found"));
    }
}