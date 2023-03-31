package buchneva.tests;

//package com.wegotrip;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static io.restassured.http.ContentType.JSON;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.notNullValue;
//
//
//public class ReqresInTests {
//    @Test
//    void checkCreateUserWithoutParameters() {
//        given()
//                .when()
//                .post("https://reqres.in/api/users")
//                .then()
//                .body("id", notNullValue());
//    }
//    @Test
//    void checkCreateUserWithParameters() {
//        Faker faker = new Faker();
//        String job = faker.job().position();
//        String name = faker.name().fullName();
//        String random = faker.random().toString();
//        String data = "{ \"name\": \"" +name + "\", \"job\": \"" + job + "\", \"random\": \"" + random + "\" }";
//        given()
//                .contentType(JSON)
//                .body(data)
//                .when()
//                .post("https://reqres.in/api/users")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue(),
//                        "name",is(name),
//                        "job", is(job),
//                        "random",is(random));
//    }
//    @Test
//    void checkCountUsersPerPage() {
//        given()
//                .when()
//                .get("https://reqres.in/api/users")
//                .then()
//                .statusCode(200)
//                .body("data.id", hasSize(Integer.parseInt("6")));
//    }
//    @Test
//    void checkNonexistentUserOnFirstPage() {
//        Faker faker = new Faker();
//        String email = faker.internet().emailAddress();
//        given()
//                .when()
//                .get("https://reqres.in/api/users")
//                .then()
//                .statusCode(200)
//                .body("data.email", not(email));
//    }
//    @Test
//    void checkExistUserOnFirstPage() {
//        given()
//                .when()
//                .get("https://reqres.in/api/users")
//                .then()
//                .statusCode(200)
//                .body("data.email", hasItem("janet.weaver@reqres.in"));
//    }
//
//}
//

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
