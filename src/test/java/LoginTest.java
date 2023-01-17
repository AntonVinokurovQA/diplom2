import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.Credentials;
import json.User;
import json.UserGenerator;
import org.junit.Before;
import org.junit.Test;
import steps.AuthAssertions;
import steps.AuthClient;
import steps.UserClient;

public class LoginTest {
    Credentials credentials;
    AuthClient authClient = new AuthClient();
    AuthAssertions authAssertions = new AuthAssertions();
    private String badLogin = "test";
    @Before
    public void setUp() {
        User tempUser = UserGenerator.getRandomUser();
        UserClient userClient = new UserClient();
        userClient.createUser(tempUser);
        credentials = new Credentials(tempUser.getEmail(), tempUser.getPassword());
    }

    @Test
    @DisplayName("Log in with valid credentials success")
    public void logInSuccess() {
        ValidatableResponse logInResponse = authClient.logIn(credentials);

        authAssertions.logInSuccessful(logInResponse);
    }

    @Test
    @DisplayName("Log in without email fail")
    public void logInWithoutEmailFail() {
        credentials.setEmail("");
        ValidatableResponse logInResponse = authClient.logIn(credentials);

        authAssertions.logInFailed(logInResponse);
    }

    @Test
    @DisplayName("Log in without password fail")
    public void logInWithoutPassFail() {
        credentials.setPassword("");
        ValidatableResponse logInResponse = authClient.logIn(credentials);

        authAssertions.logInFailed(logInResponse);
    }

    @Test
    @DisplayName("Log in without password fail")
    public void logInWithIncorrectLogin() {
        credentials.setEmail(badLogin);
        ValidatableResponse logInResponse = authClient.logIn(credentials);

        authAssertions.logInFailed(logInResponse);
    }


}
