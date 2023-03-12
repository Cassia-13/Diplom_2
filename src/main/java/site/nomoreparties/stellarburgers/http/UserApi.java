package site.nomoreparties.stellarburgers.http;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.user.User;
import site.nomoreparties.stellarburgers.model.user.UserResponse;

import static io.restassured.RestAssured.given;

public class UserApi extends StellarBurgersRestClient {
    private final String registrationApi = "/api/auth/register";
    private final String authApi = "/api/auth/login";
    private final String removeApi = "/api/auth/user";
    private final String changedApi = "api/auth/user";

    @Step("User registration. POST '/api/auth/register'")
    public Response registrationResponse(User user) {
        return given()
                .spec(baseSpec())
                .body(user)
                .when()
                .post(registrationApi);
    }

    @Step("User authorization. POST '/api/auth/login'")
    public Response authResponse(User user) {
        Response response = given()
                .spec(baseSpec())
                .body(user)
                .when()
                .post(authApi);

        user.setAccessToken(response.jsonPath().getString("accessToken"));

        return response;

    }

    @Step("Remove user. DELETE '/api/auth/user'")
    public Response removeResponse(UserResponse userResponse) {
        return given()
                .spec(baseSpec())
                .header("Authorization", userResponse.getAccessToken())
                .when()
                .delete(removeApi);
    }

    @Step("Changing a user. PATCH 'api/auth/user', use auth: {useAuth}")
    public Response changedResponse(User user, boolean useAuth) {

        Header header;
        if (useAuth) {
            header = new Header("Authorization", user.getAccessToken());
        } else {
            header = new Header("", "");
        }

        return given()
                .spec(baseSpec())
                .header(header)
                .body(user)
                .when()
                .patch(changedApi);
    }
}
