package buchneva.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresInMyTests {

    // GET LIST USERS tests https://reqres.in/api/users?page=2

    @Test
    public void getFirsNamesTest() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.first_name", hasItems("Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel"));
    }

    @Test
    public void avatarTest() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.findAll { it.id == 11 }.avatar", hasItems("https://reqres.in/img/faces/11-image.jpg"));
    }


    @Test
    public void idNotNullTest() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.id", notNullValue());
    }

    // POST https://reqres.in/api/register
    //{ "email": "eve.holt@reqres.in", "password": "pistol" }
    //{ "id": 4, "token": "QpwL5tke4Pnpja7X4" }

    @Test
    public void unSuccessfulRegistrationWithIncorrectEmailTest() {
        String data = "{ \"email\": \"eve.holt@reqres.inBB\", \"password\": \"pistol\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().body()
                .statusCode(400);
    }
}
