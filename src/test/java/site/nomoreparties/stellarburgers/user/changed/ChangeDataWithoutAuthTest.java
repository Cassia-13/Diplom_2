package site.nomoreparties.stellarburgers.user.changed;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.user.UserResponse;
import site.nomoreparties.stellarburgers.DefaultTest;

import static org.hamcrest.Matchers.equalTo;

public class ChangeDataWithoutAuthTest extends DefaultTest {

    @Before
    public void createUser() {
        userApi.registrationResponse(user);
    }

    @Test
    @DisplayName("Changing user's name without authorization")
    public void changingNameTest() {
        String name = user.getName();
        user.setName(faker.name().firstName());

        userApi.changedResponse(user, false)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("User with such email already exists")); //не совпабает ошибка и в постмане

        user.setName(name);
    }

    @Test
    @DisplayName("Changing user's email without authorization")
    public void changeEmailTest() {
        String email = user.getEmail();
        user.setEmail(faker.name().firstName());

        userApi.changedResponse(user, false)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("User with such email already exists")); //не совпабает ошибка и в постмане

        user.setEmail(email);
    }

    @Test
    @DisplayName("Changing user's password without authorization")
    public void changePasswordTest() {
        String password = user.getPassword();
        user.setPassword(faker.name().firstName());

        userApi.changedResponse(user, false)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("User with such email already exists")); //не совпабает ошибка и в постмане

        user.setPassword(password);
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
