package br.app.fsantana.marketspaceapi.api.controllers;

import br.app.fsantana.marketspaceapi.configs.TestIntegrationConfig;
import br.app.fsantana.marketspaceapi.domain.models.File;
import br.app.fsantana.marketspaceapi.domain.models.Product;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

/**
 * Created by felip on 27/10/2025.
 */

class ProductImagesControllerIT extends TestIntegrationConfig  {

    @Test
    @DisplayName("should return 404 when upload file of a product that not exists")
    public void test0(){
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
    public void test1() {
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
    @DisplayName("should return 200 when upload product file with success")
    public void test2()  {
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
                .body("imageUrl", notNullValue())
                .body("imageUrl", notNullValue());
    }

    @Test
    @DisplayName("should return 404 when delete product file not exits")
    public void test3()  {
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
    public void test4()  {
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
    public void test5()  {
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
    public void test6()  {
        Auth auth = token();
        Product product = createProduct(auth.getUser());
        File productImage = createProductImage(product);

        given()
                .auth().oauth2(auth.getToken())
                .delete("/products/{productId}/image/{imageid}", product.getId(), productImage.getId())
                .then()
                .statusCode(204);
    }
}