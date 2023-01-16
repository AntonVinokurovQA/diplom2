import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.User;
import json.UserGenerator;
import org.junit.Test;
import steps.UserAssertions;
import steps.UserClient;

public class UserTest {
    private UserClient userClient = new UserClient();
    private UserAssertions userAssertions = new UserAssertions();
    @Test
    @DisplayName("Create User with valid credentials successful")
    public void createUserSuccessful(){
        User user = UserGenerator.getRandomUser();

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.createdSuccessful(createUserResponse);
    }

    @Test
    @DisplayName("Create duplicate User failed")
    public void createDuplicatedUserIsFailed(){
        User user = UserGenerator.getRandomUser();
        userClient.createUser(user);

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.duplicatedError(createUserResponse);
    }

    @Test
    @DisplayName("Create User without email failed")
    public void createUserWithoutEmailIsFailed(){
        User user = UserGenerator.getRandomUser();
        user.setEmail("");
        userClient.createUser(user);

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.notRequiredFieldError(createUserResponse);
    }

    @Test
    @DisplayName("Create User without name failed")
    public void createUserWithoutNameIsFailed(){
        User user = UserGenerator.getRandomUser();
        user.setName("");
        userClient.createUser(user);

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.notRequiredFieldError(createUserResponse);
    }

    @Test
    @DisplayName("Create User without password failed")
    public void createUserWithoutPassIsFailed(){
        User user = UserGenerator.getRandomUser();
        user.setPassword("");
        userClient.createUser(user);

        ValidatableResponse createUserResponse = userClient.createUser(user);
        userAssertions.notRequiredFieldError(createUserResponse);
    }
}
