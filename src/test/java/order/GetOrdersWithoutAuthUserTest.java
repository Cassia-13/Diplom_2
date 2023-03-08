package order;

import http.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import model.order.OrderResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersWithoutAuthUserTest {

    OrderApi orderApi = new OrderApi();

    @Test
    @DisplayName("Get user's orders without authorization. Checking  the status code")
    public void getOrdersUserStatusCodeTest () {
        orderApi.getOrdersUserResponse()
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Get user's orders without authorization. Checking the body")
    public void getOrdersUserBodyTest () {
        orderApi.getOrdersUserResponse()
                .then()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
