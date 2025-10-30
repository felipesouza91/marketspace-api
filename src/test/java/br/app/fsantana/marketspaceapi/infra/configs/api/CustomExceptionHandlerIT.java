package br.app.fsantana.marketspaceapi.infra.configs.api;

import br.app.fsantana.marketspaceapi.api.requests.ProductCreateRequest;
import br.app.fsantana.marketspaceapi.configs.TestIntegrationConfig;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import io.restassured.http.ContentType;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.instancio.Select.field;

/**
 * Created by felip on 29/10/2025.
 */

class CustomExceptionHandlerIT extends TestIntegrationConfig  {

    @Test
    @DisplayName("should return 405 when http method not exits")
    void test0() {

        Auth auth = token();

        given()
                .auth().oauth2(auth.getToken())
                .when()
                .post("/me")
                .then()
                .statusCode(405)
                .body("detail", is("O metodo requisitado não e suportado"));
    }

    @Test
    @DisplayName("should return 400 when send invalid field")
    void test1() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "Jhn Doe");
        body.put("email", "johndoe@email.com");
        body.put("password", "1234567");
        body.put("tel", "559999899999");
        body.put("invalidField", "invalidfield");
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(400)
                .body("detail", is("A propriedade 'invalidField' não existe. Corrija ou remova essa propriedade e tente novamente."));
    }

    @Test
    @DisplayName("should return 201 when save product")
    void test2() {
        Auth auth = token();
        String paymentMethod = Instancio.gen().oneOf(getPaymentKeys()).get();
        ProductCreateRequest sample = Instancio.of(ProductCreateRequest.class)
                .set(field(ProductCreateRequest::getPaymentMethods), Set.of(paymentMethod))
                .create();
        Map<String, Object> body = new HashMap<>();
        body.put("name", sample.getName());
        body.put("description", sample.getDescription());
        body.put("isNew", "teste");
        body.put("price", "559999899999");
        body.put("acceptTrade", "true");
        body.put("paymentMethods", sample.getPaymentMethods());


        given()
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(auth.getToken())
                .when()
                .post("/products")
                .then()
                .statusCode(400)
                .body("detail", is("A propriedate 'isNew' recebeu o valor 'teste' que é de um tipo invalido. Corrija e informe um valor compativel com o tipo 'Boolean'."));

    }
}