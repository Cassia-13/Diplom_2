package user.create;

import com.github.javafaker.Faker;
import http.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.user.UserResponse;
import model.user.User;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    private final UserApi userApi = new UserApi();
    private final Faker faker = new Faker();

    User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());

    @Test
    @DisplayName("Creating unique user. Checking the status code")
    public void createUniqueUserStatusCodeTest() {
        userApi.registrationResponse(user)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Creating unique user. Checking the body")
    public void createUniqueUserBodyTest() {
        userApi.registrationResponse(user)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("accessToken", Matchers.anything())
                .body("refreshToken", Matchers.anything())
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()));
    }

    @Test
    @DisplayName("Creating a user with to an already data. Checking the status code")
    public void createTwinUserTest() {
        userApi.registrationResponse(user);
        userApi.registrationResponse(user)
                .then()
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @After
    public void removeUser() {
        UserResponse userResponse = userApi.authResponse(user)
                .body().as(UserResponse.class);

        userApi.removeResponse(userResponse)
                .then()
                .assertThat()
                .statusCode(202);
    }
}
