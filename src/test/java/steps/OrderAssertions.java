package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import json.Credentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public class OrderAssertions {
    @Step
    public void orderWithoutAuthSuccessful(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("order.number", not(empty()));
    }

    @Step
    public void orderWithAuthSuccessful(ValidatableResponse response, Credentials credentials) {
        response
                .assertThat().log().all()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("order.owner.email", equalTo(credentials.getEmail().toLowerCase()));
    }

    @Step
    public void orderWithBadIngredient(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Step
    public void unauthorizedFailed(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Step
    public void getOrderSuccessful(ValidatableResponse response) {
        response
                .assertThat().log().all()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("orders", not(empty()));
    }
}
