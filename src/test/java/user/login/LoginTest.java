package user.login;

import com.github.javafaker.Faker;
import http.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.user.UserResponse;
import model.user.User;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
public class LoginTest {
    Faker faker = new Faker();
    UserApi userApi = new UserApi();
    User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());

    @Before
    public void createUser() {
        userApi.registrationResponse(user);
    }

    @Test
    @DisplayName("Login user. Checking the status code")
    public void loginStatusCodeTest() {
        userApi.authResponse(user)
                .then()
                .assertThat()
                .statusCode(200)
                .body( "success", equalTo(true));
    }

    @Test
    @DisplayName("Login user. Checking the body")
    public void loginBodyTest() {
        userApi.authResponse(user)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("accessToken",Matchers.anything())
                .body("refreshToken", Matchers.anything())
                .body("user.email",  equalTo(user.getEmail()))
                .body("user.name",  equalTo(user.getName()));
    }

    @Test
    @DisplayName("Login user with incorrect email")
    public void loginWithIncorrectEmailTest() {
        String email = user.getEmail();
        user.setEmail(faker.name().username());
        userApi.authResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo( "email or password are incorrect"));

        user.setEmail(email);
    }

    @Test
    @DisplayName("Login user with incorrect password")
    public void loginWithIncorrectPasswordTest() {
        String password = user.getPassword();
        user.setPassword(faker.random().hex());
        userApi.authResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo( "email or password are incorrect"));
        user.setPassword(password);
    }

    @Test
    @DisplayName("Login user without email")
    public void loginWithoutEmailTest() {
        String email = user.getEmail();
        user.setEmail(null);
        userApi.authResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo( "email or password are incorrect"));

        user.setEmail(email);
    }

    @Test
    @DisplayName("Login user without password")
    public void loginWithWithoutPasswordTest() {
        String password = user.getPassword();
        user.setPassword(null);
        userApi.authResponse(user)
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo( "email or password are incorrect"));
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
