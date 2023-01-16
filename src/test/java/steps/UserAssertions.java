package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UserAssertions {
    @Step
    public void createdSuccessful(ValidatableResponse response){
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
    }

    @Step
    public void duplicatedError(ValidatableResponse response){
        response
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Step
    public void notRequiredFieldError(ValidatableResponse response){
        response
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
