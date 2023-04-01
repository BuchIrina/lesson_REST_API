package buchneva.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {

    /*
    1. request https://reqres.in/api/login
    2. POST { "email": "eve.holt@reqres.in", "password": "cityslicka" }
    { "token": "QpwL5tke4Pnpja7X4" }
    3. check token:QpwL5tke4Pnpja7X4
    */
    @Test
    public void loginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    // negative test - without data type
    @Test
    public void unSupportedMediaTypeTest() {
        given()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(415);
    }

    // negative test - invalid data
    @Test
    public void missingEmailOrPasswordTest() {
        given()
                .body("123")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    // negative test - missimg password
    @Test
    public void missingPasswordTest() {

        String data = "{ \"email\": \"eve.holt@reqres.in\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
