package buchneva;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {

    /*
    1. request: https://selenoid.autotests.cloud/status
    2. GET response { total: 20, used: 0, queued: 0, pending: 0, browsers: { android: { 8.1: { } },
    chrome: { 100.0: { }, 99.0: { } }, chrome-mobile: { 86.0: { } }, firefox: { 97.0: { }, 98.0: { } },
    opera: { 84.0: { }, 85.0: { } } } }
    3. check: total: 20
    */

    @Test
    public void totalTest() {
        given().
                //with logs
                        log().all().
                /*
                or log.uri
                log.status
                log.body
                */
                        when().
                get("https://selenoid.autotests.cloud/status")
                .then().
                log().all()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    public void browserTest() {
        given().
                log().uri().
                when().
                get("https://selenoid.autotests.cloud/status")
                .then().
                log().status()
                .log().body()
                .statusCode(200)
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    public void checkResponseBadPractice() {
        String expectedResponseString = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":" +
                "{\"android\":{\"8.1\":{}}," +
                "\"chrome\":{\"100.0\":{},\"99.0\":{}}," +
                "\"chrome-mobile\":{\"86.0\":{}}," +
                "\"firefox\":{\"97.0\":{},\"98.0\":{}}," +
                "\"opera\":{\"84.0\":{},\"85.0\":{}}}}\n";

        Response actualResponse = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();

        assertEquals(expectedResponseString, actualResponse.asString());
    }

    @Test
    void checkResponseGoodPractice() {
        Integer expectedTotal = 20;

        Integer actualTotal = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("total");

        assertEquals(expectedTotal, actualTotal);
    }

     /*
    1. request: https://selenoid.autotests.cloud/wd/hub/status
    2. GET response { value: { message: "Selenoid 1.10.7 built at 2021-11-21_05:46:32AM", ready: true } }
    3. check:value.ready: true
    */

    @Test
    public void statusWDHub401Test() {
        given().
                log().uri().
                when().
                get("https://selenoid.autotests.cloud/wd/hub/status")
                .then().
                log().status()
                .log().body()
                .statusCode(401);
    }

    @Test
    public void statusWDHubWithValueTest() {
        given().
                log().uri().
                when().
                get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then().
                log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    public void statusWDHubWithAuthTest() {
        given().
                log().uri()
                .auth().basic("user1", "1234").
                when().
                get("https://selenoid.autotests.cloud/wd/hub/status")
                .then().
                log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
