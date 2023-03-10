package user.changed;

import com.github.javafaker.Faker;
import http.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.user.UserResponse;
import model.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangedDataWithoutAuthTest {
    private final Faker faker = new Faker();
    private final UserApi userApi = new UserApi();
    private final User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());

    @Before
    public void createUser() {
        userApi.registrationResponse(user);
    }

    @Test
    @DisplayName("Changing user's name without authorization")
    public void changingNameTest() {
        String name = user.getName();
        user.setName(faker.name().firstName());

        userApi.changedResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body( "success", equalTo(false))
                .body("message", equalTo("User with such email already exists")); //не совпабает ошибка и в постмане

        user.setName(name);
    }

    @Test
    @DisplayName("Changing user's email without authorization")
    public void changingEmailTest() {
        String email = user.getEmail();
        user.setEmail(faker.name().firstName());

        userApi.changedResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body( "success", equalTo(false))
                .body("message", equalTo("User with such email already exists")); //не совпабает ошибка и в постмане

        user.setEmail(email);
    }

    @Test
    @DisplayName("Changing user's password without authorization")
    public void changingPasswordTest() {
        String password = user.getPassword();
        user.setPassword(faker.name().firstName());

        userApi.changedResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body( "success", equalTo(false))
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
