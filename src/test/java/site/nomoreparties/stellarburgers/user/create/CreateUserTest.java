package site.nomoreparties.stellarburgers.user.create;

import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.user.UserResponse;
import site.nomoreparties.stellarburgers.DefaultTest;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends DefaultTest {

    @Test
    @DisplayName("Creating unique user. Checking the status code")
    public void createUniqueUserTest() {
        userApi.registrationResponse(user)
                .then()
                .assertThat()
                .statusCode(200)
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
