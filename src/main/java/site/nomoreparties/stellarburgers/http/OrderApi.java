package site.nomoreparties.stellarburgers.http;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.order.Order;
import site.nomoreparties.stellarburgers.model.user.User;
import site.nomoreparties.stellarburgers.model.user.UserResponse;

import static io.restassured.RestAssured.given;

public class OrderApi extends StellarBurgersRestClient {
    private final String createApi = "/api/orders";
    private final String getOrdersUserApi = "/api/orders";

    @Step("Create a order with authorization. Post '/api/orders'")
    public Response createResponse(UserResponse userResponse, Order order) {
        return given()
                .spec(baseSpec())
                .header("Authorization", userResponse.getAccessToken())
                .body(order)
                .when()
                .post(createApi);
    }

    @Step("Create a order without authorization. Post '/api/orders'")
    public Response createResponse(Order order) {
        return given()
                .spec(baseSpec())
                .body(order)
                .when()
                .post(createApi);
    }

    @Step("Get user's orders. Get '/api/orders', use auth: {useAuth} ")
    public Response getOrdersUserResponse(User user, boolean useAuth) {

        Header header;

        if (useAuth) {
            header = new Header("Authorization", user.getAccessToken());
        } else {
            header = new Header("", "");
        }
        return given()
                .spec(baseSpec())
                .header(header)
                .when()
                .get(getOrdersUserApi);
    }
}
