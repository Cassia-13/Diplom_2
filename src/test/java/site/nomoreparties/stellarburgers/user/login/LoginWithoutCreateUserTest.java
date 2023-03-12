package site.nomoreparties.stellarburgers.user.login;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import site.nomoreparties.stellarburgers.DefaultTest;
import site.nomoreparties.stellarburgers.model.user.User;

import static org.hamcrest.Matchers.equalTo;

public class LoginWithoutCreateUserTest extends DefaultTest {

    @Test
    @DisplayName("Login user without data")
    public void loginWithoutDataTest() {
        User user = new User();

        userApi.authResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login user with incorrect data")
    public void loginWithIncorrectDataTest() {
        User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());

        userApi.authResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
