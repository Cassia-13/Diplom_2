package site.nomoreparties.stellarburgers.user.create;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import site.nomoreparties.stellarburgers.DefaultTest;
import site.nomoreparties.stellarburgers.model.user.UserResponse;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserWithoutRequiredFieldTest extends DefaultTest {

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

        userApi.registrationResponse(user).body().as(UserResponse.class);
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
    }

    @After
    public void removeUser() {
        userApi.registrationResponse(user).body().as(UserResponse.class);

        if (userResponse.getAccessToken() != null) {
            userApi.removeResponse(userResponse).then().statusCode(202);
        }
    }
}
