package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import json.User;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.*;

public class UserAssertions {
    @Step
    public void createdSuccessful(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
    }

    @Step
    public void duplicatedFailed(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Step
    public void notRequiredFieldFailed(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step
    public void getUserSuccessful(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("user.email", not(empty()))
                .body("user.name", not(empty()));
    }

    @Step
    public void updateUserSuccessful(ValidatableResponse response, User user) {
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("user.email", equalTo(user.getEmail().toLowerCase()))
                .body("user.name", equalTo(user.getName()));
    }

    @Step
    public void unauthorisedError(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));
    }
}
