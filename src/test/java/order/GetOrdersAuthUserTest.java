package order;

import com.github.javafaker.Faker;
import http.OrderApi;
import http.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.order.OrderResponse;
import model.user.User;
import model.user.UserResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersAuthUserTest {
    private final Faker faker = new Faker();
    private final OrderApi orderApi = new OrderApi();
    private final UserApi userApi = new UserApi();
    private UserResponse userResponse = new UserResponse();

    private final User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());

    @Before
    public void createUser() {
        Faker faker = new Faker();
        UserApi userApi = new UserApi();
        User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());

        userApi.registrationResponse(user);
        userResponse = userApi.authResponse(user)
                .body().as(UserResponse.class);
    }

    @Test
    @DisplayName("Get user's orders with authorization. Checking the status code")
    public void getOrdersUserStatusCodeTest () {
        orderApi.getOrdersUserResponse(userResponse)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }


    @Test
    @DisplayName("Get user's orders with authorization. Checking the body")
    public void getOrdersUserBodyTest () {
        OrderResponse orderResponse = orderApi.getOrdersUserResponse(userResponse)
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
