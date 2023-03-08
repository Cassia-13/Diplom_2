package user.create;

import com.github.javafaker.Faker;
import http.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.user.User;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserWithoutRequiredFieldTest {
    UserApi userApi = new UserApi();
    Faker faker = new Faker();
    User user = new User(faker.address().city() + "@piu.ru", faker.random().hex(), faker.name().firstName());

    @Test
    @DisplayName("Creating unique user without email")
    public void createWithoutEmailTest() {
        String email = user.getEmail();
        user.setEmail(null);
        userApi.registrationResponse(user)
                .then()
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));

        user.setEmail(email);
    }

    @Test
    @DisplayName("Creating unique user without password")
    public void createWithoutPasswordTest() {
        String password = user.getPassword();
        user.setPassword(null);
        userApi.registrationResponse(user)
                .then()
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));

        user.setPassword(password);
    }

    @Test
    @DisplayName("Creating unique user without name")
    public void createWithoutNameTest() {
        String name = user.getName();
        user.setName(null);
        userApi.registrationResponse(user)
                .then()
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));

        user.setName(name);
    }
}
