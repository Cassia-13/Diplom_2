package http;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.user.UserResponse;
import model.user.User;

import static io.restassured.RestAssured.given;

public class UserApi extends StellarBurgersRestClient {

    @Step("User registration. POST '/api/auth/register'")
    public Response registrationResponse(User user) {
        return given()
                .spec(baseSpec())
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step("User authorization. POST '/api/auth/login'")
    public Response authResponse(User user){
       return given()
                .spec(baseSpec())
                .body(user)
                .when()
                .post("/api/auth/login");
    }

    @Step("Remove user. DELETE '/api/auth/user'")
    public Response removeResponse(UserResponse userResponse) {
        return given()
                .spec(baseSpec())
                .header("Authorization", userResponse.getAccessToken())
                .when()
                .delete("/api/auth/user");
    }

    @Step("User changes with authorization. PATCH 'api/auth/user'")
    public Response changedResponse(UserResponse userResponse, User user) {
        return given()
                .spec(baseSpec())
                .header("Authorization", userResponse.getAccessToken())
                .body(user)
                .when()
                .patch("api/auth/user");
    }

    @Step("Changing a user without authorization. PATCH 'api/auth/user'")
    public Response changedResponse(User user) {
        return given()
                .spec(baseSpec())
                .body(user)
                .when()
                .patch("api/auth/user");
    }
}
