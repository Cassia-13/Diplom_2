package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.order.OrderResponse;
import site.nomoreparties.stellarburgers.model.user.UserResponse;
import site.nomoreparties.stellarburgers.DefaultTest;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersAuthUserTest extends DefaultTest {

    @Before
    public void createUser() {
        userApi.registrationResponse(user);
        userResponse = userApi.authResponse(user)
                .body().as(UserResponse.class);
    }

    @Test
    @DisplayName("Get user's orders with authorization. Checking the status code")
    public void getOrdersUserStatusCodeTest() {
        orderApi.getOrdersUserResponse(user,  true)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }


    @Test
    @DisplayName("Get user's orders with authorization. Checking the body")
    public void getOrdersUserBodyTest() {
        OrderResponse orderResponse = orderApi.getOrdersUserResponse(user, true)
                .body().as(OrderResponse.class);

        Assert.assertNotNull(orderResponse);
    }

    @After
    public void removeUser() {
        userApi.removeResponse(userResponse)
                .then()
                .statusCode(202);
    }
}
