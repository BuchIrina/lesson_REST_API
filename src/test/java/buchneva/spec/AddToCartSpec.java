package buchneva.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static buchneva.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class AddToCartSpec {
    public static RequestSpecification addToCartRequest = with()
            .filter(withCustomTemplates())
            .log().uri()
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .baseUri("https://demowebshop.tricentis.com")
            .basePath("/addproducttocart");

    public static ResponseSpecification addToCartResponse = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .build();
}
