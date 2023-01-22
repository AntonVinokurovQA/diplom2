package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import json.Credentials;
import json.User;

import static data.TestData.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class UserClient {
    @Step
    public ValidatableResponse createUser(User user) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .post(NEW_USER_URI)
                .then();
    }

    @Step
    public ValidatableResponse deleteUser(String token) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .and()
                .when()
                .delete(USER_URI)
                .then();
    }

    @Step
    public void deleteUser(User user) {
        Credentials credentials = new Credentials(user.getEmail(), user.getPassword());
        AuthClient authClient = new AuthClient();
        UserClient userClient = new UserClient();

        if (authClient.logIn(credentials).extract().statusCode() == SC_OK) {
            String token = authClient.logIn(credentials).extract().path("accessToken");
            userClient.deleteUser(token);
        }
    }

    @Step
    public ValidatableResponse getUser(String token) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .and()
                .when()
                .get(USER_URI)
                .then();
    }

    @Step
    public ValidatableResponse updateUser(String token, User user) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .and()
                .body(user)
                .when()
                .patch(USER_URI)
                .then();
    }

    @Step
    public ValidatableResponse updateUser(User user) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .patch(USER_URI)
                .then();
    }
}
