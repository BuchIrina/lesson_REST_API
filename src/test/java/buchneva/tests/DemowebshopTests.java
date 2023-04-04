package buchneva.tests;

import buchneva.models.lombok.AddToCartRequestModel;
import buchneva.models.lombok.AddToCartResponseModel;
import org.junit.jupiter.api.Test;

import static buchneva.spec.AddToCartSpec.addToCartRequest;
import static buchneva.spec.AddToCartSpec.addToCartResponse;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DemowebshopTests {


    @Test
    public void addToCartTest() {
        AddToCartRequestModel add = new AddToCartRequestModel();
        add.setBody("product_attribute_72_5_18=53" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&addtocart_72.EnteredQuantity=1");
        add.setCookie("13887C94DA4B3C68A2204282D54225F0EB85CA" +
                "DD173579418F83C50469469707DDEC29E0B2F76EBBB3985" +
                "074E5C5D61EC972ECFC025664559688D89B76AC0B62324D" +
                "33FD88E26E9DCCE16FC150D4714D0383F7F237637087458" +
                "DD0F32BCFA9751CE388340BDDBF3359DCD1C2E117C9D1108" +
                "440DB08728B6C8FE89A8B598625E71DE7A2BD4C8C1352137" +
                "36FB804778E14E5D1B9E4E8B4413CEE2074D2D5CE54EA755F" +
                "B94780EACDF6E1DAA6C6466E3628;");


        AddToCartResponseModel response = step("Add goods to cart", () -> given(addToCartRequest)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", add.getCookie())
                .body(add.getBody())
                .when()
                .post("/details/72/1")
                .then()
                .spec(addToCartResponse)
                .extract().as(AddToCartResponseModel.class));

        step("Check that success is true", () ->
                assertThat(response.getSuccess()).isEqualTo(true));
    }
}
