package buchneva.tests;

import buchneva.models.lombok.LoginBodyLombokModel;
import buchneva.models.lombok.LoginResponseLombokModel;
import buchneva.models.pojo.LoginBodyModel;
import buchneva.models.pojo.LoginResponseModel;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static buchneva.helpers.CustomAllureListener.withCustomTemplates;
import static buchneva.spec.LoginSpec.loginRequestSpec;
import static buchneva.spec.LoginSpec.loginResponseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresInExtendedTests {

    @Test
    public void loginPogoTest() {

        LoginBodyModel bodyModel = new LoginBodyModel();
        bodyModel.setEmail("eve.holt@reqres.in");
        bodyModel.setPassword("cityslicka");

        LoginResponseModel responseModel = given()
                .filter(new AllureRestAssured())
                .contentType(JSON)
                .body(bodyModel)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        assertThat(responseModel.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    public void loginLombokTest() {

        LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(loginBody)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }


    @Test
    public void loginLombokWithStepsTest() {

        LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = step("Get authorization data", () ->
                given(loginRequestSpec)
                        .body(loginBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Verify authorization response", () ->
                assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }
}
