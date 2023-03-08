package http;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.Order;
import model.user.UserResponse;

import static io.restassured.RestAssured.given;

public class OrderApi extends StellarBurgersRestClient {

    @Step("Create a order with authorization. Post '/api/orders'")
    public Response createResponse(UserResponse userResponse, Order order) {
        return given()
                .spec(baseSpec())
                .header("Authorization", userResponse.getAccessToken())
                .body(order)
                .when()
                .post("/api/orders");
    }

    @Step("Create a order without authorization. Post '/api/orders'")
    public Response createResponse(Order order) {
        return given()
                .spec(baseSpec())
                .body(order)
                .when()
                .post("/api/orders");
    }

    @Step("Get user's orders with authorization. Get '/api/orders' ")
    public Response getOrdersUserResponse(UserResponse userResponse) {
        return given()
                .spec(baseSpec())
                .header("Authorization", userResponse.getAccessToken())
                .when()
                .get("/api/orders");
    }

    @Step("Get user's orders without authorization. Get '/api/orders' ")
    public Response getOrdersUserResponse() {
        return given()
                .spec(baseSpec())
                .when()
                .get("/api/orders");
    }
}
