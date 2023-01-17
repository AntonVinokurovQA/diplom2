package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public class AuthAssertions {
    @Step
    public void logInSuccessful(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("accessToken", not(empty()))
                .body("refreshToken", not(empty()));
    }

    @Step
    public void logInFailed(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
