package site.nomoreparties.stellarburgers.model.user;

import java.util.Objects;

public class UserResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    public UserResponse(boolean success, User user, String accessToken, String refreshToken) {
        this.success = success;
    }

    public UserResponse(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public UserResponse() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse userResponse = (UserResponse) o;
        return success == userResponse.success && user.equals(userResponse.user) && Objects.equals(accessToken, userResponse.accessToken) && Objects.equals(refreshToken, userResponse.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, user, accessToken, refreshToken);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
