package buchneva.tests;

import buchneva.models.lombok.LoginResponseLombokModel;
import buchneva.models.lombok.Users;
import buchneva.models.lombok.UsersResponseModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static buchneva.spec.LoginSpec.loginRequestSpec;
import static buchneva.spec.LoginSpec.loginResponseSpec;
import static buchneva.spec.LoginUnsuccessfulSpec.loginUnsuccessfulRequestSpec;
import static buchneva.spec.LoginUnsuccessfulSpec.loginUnsuccessfulResponseSpec;
import static buchneva.spec.UsersSpec.userRequestSpec;
import static buchneva.spec.UsersSpec.userResponseSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;

public class MyTestsWithSpecAndLombok {
    @Test
    public void getTotalTest() {
        UsersResponseModel responseModel = given(userRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(userResponseSpec)
                .extract().as(UsersResponseModel.class);

        assertThat(responseModel.getTotal()).isEqualTo(12);

    }

    @Test
    public void getFirsNamesTest() {
//        String[] firstNames = {"Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel"};

        UsersResponseModel responseModel = given(userRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(userResponseSpec)
                .extract().as(UsersResponseModel.class);

        assertThat(responseModel.getData()).extracting(Users::getFirstName)
                .contains("Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel");

//        for (int i = 0; i < responseModel.getData().size(); i++) {
//            assertThat(responseModel.getData().get(i).getFirstName()).isEqualTo(firstNames[i]);
//        }
    }

    @Test
    public void avatarUserWithId11Test() {
        UsersResponseModel responseModel = given(userRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(userResponseSpec)
                .extract().as(UsersResponseModel.class);

        assertThat(responseModel.getData()).filteredOn("id", 11)
                .extracting(Users::getAvatar)
                .contains("https://reqres.in/img/faces/11-image.jpg");
    }


    @Test
    public void idNotNullTest() {
        UsersResponseModel responseModel = given(userRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(userResponseSpec)
                .extract().as(UsersResponseModel.class);

        assertThat(responseModel.getData()).extracting(Users::getId).isNotNull();
//                .body("data.id", notNullValue());
    }

    // POST https://reqres.in/api/register
    //{ "email": "eve.holt@reqres.in", "password": "pistol" }
    //{ "id": 4, "token": "QpwL5tke4Pnpja7X4" }

    @Test
    public void unSuccessfulRegistrationWithIncorrectEmailTest() {
        String data = "{ \"email\": \"eve.holt@reqres.inBB\", \"password\": \"pistol\" }";

        LoginResponseLombokModel responseLombokModel = given(loginUnsuccessfulRequestSpec)
                .body(data)
                .when()
                .post("/register")
                .then()
                .spec(loginUnsuccessfulResponseSpec)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(responseLombokModel.getError()).isEqualTo("Note: Only defined users succeed registration");
    }
}
