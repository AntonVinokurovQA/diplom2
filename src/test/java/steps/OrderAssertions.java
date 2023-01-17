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
    public void orderSuccessful(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("order.number", not(empty()));
    }

    @Step
    public void withoutIngredientFailed(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step
    public void orderSuccessful(ValidatableResponse response, Credentials credentials) {
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("order.owner.email", equalTo(credentials.getEmail().toLowerCase()));
    }

    @Step
    public void badIngredientFailed(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Step
    public void unauthorizedFailed(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Step
    public void getOrderSuccessful(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("orders", not(empty()));
    }
}
