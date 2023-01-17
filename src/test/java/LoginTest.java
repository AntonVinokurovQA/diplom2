import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.*;
import org.junit.*;
import steps.*;

public class LoginTest {
    private AuthClient authClient = new AuthClient();
    private AuthAssertions authAssertions = new AuthAssertions();
    private UserClient userClient = new UserClient();
    private Credentials credentials;
    private String badLogin = "test";
    private String token;

    @Before
    public void setUp() {
        User tempUser = UserGenerator.getRandomUser();

        userClient.createUser(tempUser);
        credentials = new Credentials(tempUser.getEmail(), tempUser.getPassword());
        token = authClient.logIn(credentials).extract().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.deleteUser(token);
    }

    @Test
    @DisplayName("Log in with valid credentials success")
    public void logInSuccessful() {
        ValidatableResponse logInResponse = authClient.logIn(credentials);

        authAssertions.logInSuccessful(logInResponse);
    }

    @Test
    @DisplayName("Log in without email fail")
    public void logInWithoutEmailFailed() {
        credentials.setEmail("");
        ValidatableResponse logInResponse = authClient.logIn(credentials);

        authAssertions.unauthorizedFailed(logInResponse);
    }

    @Test
    @DisplayName("Log in without password fail")
    public void logInWithoutPassFailed() {
        credentials.setPassword("");
        ValidatableResponse logInResponse = authClient.logIn(credentials);

        authAssertions.unauthorizedFailed(logInResponse);
    }

    @Test
    @DisplayName("Log in without password fail")
    public void logInWithIncorrectLoginFailed() {
        credentials.setEmail(badLogin);
        ValidatableResponse logInResponse = authClient.logIn(credentials);

        authAssertions.unauthorizedFailed(logInResponse);
    }
}
