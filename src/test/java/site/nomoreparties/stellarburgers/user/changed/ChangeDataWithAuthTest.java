package site.nomoreparties.stellarburgers.user.changed;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.DefaultTest;
import site.nomoreparties.stellarburgers.model.user.User;
import site.nomoreparties.stellarburgers.model.user.UserResponse;

import static org.hamcrest.Matchers.equalTo;

public class ChangeDataWithAuthTest extends DefaultTest {

    @Before
    public void createUser() {
        userApi.registrationResponse(user);
        userResponse = userApi.authResponse(user)
                .body().as(UserResponse.class);
    }

    @Test
    @DisplayName("Changing user's name with authorization. Checking the status code")
    public void changeNameStatusCodeTest() {
        user.setName(faker.name().firstName());

        userApi.changedResponse(user, true)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Changing user's name with authorization. Checking the body")
    public void changeNameBodyTest() {
        user.setName(faker.name().firstName());
        UserResponse expected = new UserResponse(true, new User(user.getEmail(), user.getName()));

        UserResponse actual = userApi.changedResponse(user, true)
                .body().as(UserResponse.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Changing user's email with authorization. Checking the status code")
    public void changeEmailStatusCodeTest() {
        user.setEmail(faker.name().username() + "@piu.ru");

        userApi.changedResponse(user, true)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Changing user's email with authorization. Checking the body")
    public void changeEmailBodyTest() {
        user.setEmail(faker.name().username() + "@piu.ru");
        UserResponse expected = new UserResponse(true, new User(user.getEmail(), user.getName()));

        UserResponse actual = userApi.changedResponse(user, true)
                .body().as(UserResponse.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Changing user's password with authorization. Checking the status code")
    public void changePasswordStatusCodeTest() {
        user.setPassword(faker.random().hex());

        userApi.changedResponse(user, true)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Changing user's password with authorization. Checking the body")
    public void changePasswordBodyTest() {
        user.setPassword(faker.random().hex());
        UserResponse expected = new UserResponse(true, new User(user.getEmail(), user.getName()));

        UserResponse actual = userApi.changedResponse(user, true)
                .body().as(UserResponse.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Changing user's email to an already existing email")
    public void changeEmailTwinTest() { // тест падат
        User twin = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());
        userApi.registrationResponse(twin);
        userApi.authResponse(twin);

        twin.setEmail(user.getEmail());

        userApi.changedResponse(twin, true)
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
