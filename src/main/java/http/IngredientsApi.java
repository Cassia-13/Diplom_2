package http;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsApi extends StellarBurgersRestClient {

    @Step("Get the ingredients. GET '/api/ingredients'")
    public Response getIngredientsResponse() {
        return given()
                .spec(baseSpec())
                .when()
                .get("/api/ingredients");
    }
}
