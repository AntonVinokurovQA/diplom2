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
    public void duplicatedError(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Step
    public void notRequiredFieldError(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step
    public void userGetSuccessful(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("user.email", not(empty()))
                .body("user.name", not(empty()));
    }

    @Step
    public void userUpdateSuccessful(ValidatableResponse response, User user) {
        response
                .assertThat().log().all()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("user.email", equalTo(user.getEmail().toLowerCase()))
                .body("user.name", equalTo(user.getName()));
    }

    @Step
    public void userUpdateUnauthorisedError(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));
    }
}
