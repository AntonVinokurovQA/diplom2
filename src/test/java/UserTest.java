import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.Credentials;
import json.User;
import json.UserGenerator;
import org.junit.Before;
import org.junit.Test;
import steps.AuthClient;
import steps.UserAssertions;
import steps.UserClient;

public class UserTest {
    private UserClient userClient = new UserClient();
    private UserAssertions userAssertions = new UserAssertions();
    private AuthClient authClient = new AuthClient();
    private String token;
    private User user;
    private Credentials credentials;

    @Before
    public void serUp() {
        user = UserGenerator.getRandomUser();
        credentials = new Credentials(user.getEmail(), user.getPassword());
        userClient.createUser(user);
    }

    @Test
    @DisplayName("Get user info with auth")
    public void getUserInfo() {
        token = authClient.logIn(credentials).extract().path("accessToken");

        ValidatableResponse getUserResponse = userClient.getUser(token);
        userAssertions.userGetSuccessful(getUserResponse);
    }

    @Test
    @DisplayName("Update user data with auth")
    public void updateUserWithAuth() {
        token = authClient.logIn(credentials).extract().path("accessToken");
        user = UserGenerator.getRandomUser();

        ValidatableResponse getUserResponse = userClient.updateUser(token, user);
        userAssertions.userUpdateSuccessful(getUserResponse, user);
    }

    @Test
    @DisplayName("Update user data without auth")
    public void updateUserWithoutAuth() {
        user = UserGenerator.getRandomUser();

        ValidatableResponse getUserResponse = userClient.updateUser(user);
        userAssertions.userUpdateUnauthorisedError(getUserResponse);
    }
}
