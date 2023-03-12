package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import site.nomoreparties.stellarburgers.DefaultTest;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersWithoutAuthUserTest extends DefaultTest {

    @Test
    @DisplayName("Get user's orders without authorization. Checking  the status code")
    public void getOrdersUserStatusCodeTest() {
        orderApi.getOrdersUserResponse(user, false)
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Get user's orders without authorization. Checking the body")
    public void getOrdersUserBodyTest() {
        orderApi.getOrdersUserResponse(user, false)
                .then()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
