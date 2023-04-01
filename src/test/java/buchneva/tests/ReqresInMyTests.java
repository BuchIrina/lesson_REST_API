package buchneva.tests;

import buchneva.models.lombok.Users;
import buchneva.models.lombok.UsersResponseModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static buchneva.spec.UsersSpec.userRequestSpec;
import static buchneva.spec.UsersSpec.userResponseSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class ReqresInMyTests {

    // GET LIST USERS tests https://reqres.in/api/users?page=2

    @Test
    public void getTotalTest() {
        given()
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    public void getFirsNamesTest() {
        given()
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.first_name", hasItems("Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel"));

    }

    @Test
    public void avatarUserWithId11Test() {
        given()
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
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
