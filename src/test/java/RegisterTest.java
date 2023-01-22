import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.User;
import json.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserAssertions;
import steps.UserClient;

public class RegisterTest {
    private UserClient userClient = new UserClient();
    private UserAssertions userAssertions = new UserAssertions();
    private User user;

    @Before
    public void setUp() {
        user = UserGenerator.getRandomUser();
    }

    @After
    public void tearDown() {
        userClient.deleteUser(user);
    }

    @Test
    @DisplayName("Create User with valid credentials successful")
    public void createUserSuccessful() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.createdSuccessful(createUserResponse);
    }

    @Test
    @DisplayName("Create duplicate User failed")
    public void createDuplicatedUserIsFailed() {
        userClient.createUser(user);

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.duplicatedFailed(createUserResponse);
    }

    @Test
    @DisplayName("Create User without email failed")
    public void createUserWithoutEmailIsFailed() {
        user.setEmail("");
        userClient.createUser(user);

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.notRequiredFieldFailed(createUserResponse);
    }

    @Test
    @DisplayName("Create User without name failed")
    public void createUserWithoutNameIsFailed() {
        user.setName("");
        userClient.createUser(user);

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.notRequiredFieldFailed(createUserResponse);
    }

    @Test
    @DisplayName("Create User without password failed")
    public void createUserWithoutPassIsFailed() {
        user.setPassword("");
        userClient.createUser(user);

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.notRequiredFieldFailed(createUserResponse);
    }
}
