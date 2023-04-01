package buchneva.tests;

import buchneva.models.lombok.LoginBodyLombokModel;
import buchneva.models.lombok.LoginResponseLombokModel;
import buchneva.models.lombok.Users;
import buchneva.models.lombok.UsersResponseModel;
import org.junit.jupiter.api.Test;

import static buchneva.spec.LoginUnsuccessfulSpec.loginUnsuccessfulRequestSpec;
import static buchneva.spec.LoginUnsuccessfulSpec.loginUnsuccessfulResponseSpec;
import static buchneva.spec.UsersSpec.userRequestSpec;
import static buchneva.spec.UsersSpec.userResponseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class MyTestsWithSpecAndLombok {
    @Test
    public void getTotalTest() {

        UsersResponseModel responseModel = step("Get list of users", () ->
                given(userRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UsersResponseModel.class));

        step("Check total result", () ->
                assertThat(responseModel.getTotal()).isEqualTo(12));

    }

    @Test
    public void getFirsNamesTest() {
//        String[] firstNames = {"Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel"};

        UsersResponseModel responseModel = step("Get list of users", () ->
                given(userRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UsersResponseModel.class));
        step("Check list of names", () ->
                assertThat(responseModel.getData())
                        .extracting(Users::getFirstName)
                        .contains("Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel"));

//        for (int i = 0; i < responseModel.getData().size(); i++) {
//            assertThat(responseModel.getData().get(i).getFirstName()).isEqualTo(firstNames[i]);
//        }
    }

    @Test
    public void avatarUserWithId11Test() {
        UsersResponseModel responseModel = step("Get list of users", () ->
                given(userRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UsersResponseModel.class));

        step("Check that user with id 11 has avatar https://reqres.in/img/faces/11-image.jpg", () ->
                assertThat(responseModel.getData()).filteredOn("id", 11)
                        .extracting(Users::getAvatar)
                        .contains("https://reqres.in/img/faces/11-image.jpg"));
    }

    @Test
    public void unSuccessfulRegistrationWithIncorrectEmailTest() {
        LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.inBB");
        loginBody.setPassword("pistol");

        LoginResponseLombokModel responseLombokModel = step("Get authorization data", () ->
                given(loginUnsuccessfulRequestSpec)
                        .body(loginBody)
                        .when()
                        .post("/register")
                        .then()
                        .spec(loginUnsuccessfulResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check error response", () ->
                assertThat(responseLombokModel.getError()).isEqualTo("Note: Only defined users succeed registration"));
    }
}
