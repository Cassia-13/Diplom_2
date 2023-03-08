package user.changed;

import com.github.javafaker.Faker;
import http.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.user.UserResponse;
import model.user.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangedDataWithAuthTest {
    Faker faker = new Faker();
    UserApi userApi = new UserApi();
    UserResponse userResponse = new UserResponse();
    User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());


    @Before
    public void createUser() {
        userApi.registrationResponse(user);
        userResponse = userApi.authResponse(user)
                .body().as(UserResponse.class);
    }

    @Test
    @DisplayName("Changing user's name with authorization. Checking the status code")
    public void changingNameStatusCodeTest() {
        user.setName(faker.name().firstName());

        userApi.changedResponse(userResponse, user)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Changing user's name with authorization. Checking the body")
    public void changingNameBodyTest() {
        user.setName(faker.name().firstName());
        UserResponse expected = new UserResponse(true, new User(user.getEmail(), user.getName()));

        UserResponse actual = userApi.changedResponse(userResponse, user)
                .body().as(UserResponse.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Changing user's email with authorization. Checking the status code")
    public void changingEmailStatusCodeTest() {
        user.setEmail(faker.name().username() + "@piu.ru");

        userApi.changedResponse(userResponse, user)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Changing user's email with authorization. Checking the body")
    public void changingEmailBodyTest() {
        user.setEmail(faker.name().username() + "@piu.ru");
        UserResponse expected = new UserResponse(true, new User(user.getEmail(), user.getName()));

        UserResponse actual = userApi.changedResponse(userResponse, user)
                .body().as(UserResponse.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Changing user's password with authorization. Checking the status code")
    public void changingPasswordStatusCodeTest() {
        user.setPassword(faker.random().hex());

        userApi.changedResponse(userResponse, user)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Changing user's password with authorization. Checking the body")
    public void changingPasswordBodyTest() {
        user.setPassword(faker.random().hex());
        UserResponse expected = new UserResponse(true, new User(user.getEmail(), user.getName()));

        UserResponse actual = userApi.changedResponse(userResponse, user)
                .body().as(UserResponse.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Changing user's email to an already existing email")
    public void changingEmailTwinTest() { // тест падат
        User twin = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());
        twin.setEmail(user.getEmail());

        userApi.changedResponse(userResponse, twin)
                .then()
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User with such email already exists"));
    }

    @After
    public void removeUser() {
        userApi.removeResponse(userResponse)
                .then()
                .assertThat()
                .statusCode(202);
    }
}
