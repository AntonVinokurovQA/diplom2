package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import json.OrderGenerator;

import static data.TestData.BASE_URI;
import static data.TestData.ORDERS_URI;
import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step
    public ValidatableResponse createOrderWithIngredients() {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(OrderGenerator.getRandomOrder())
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse createOrderWithIngredients(String token) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .and()
                .body(OrderGenerator.getRandomOrder())
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse createOrderWithBadIngredient() {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(OrderGenerator.getOrderWithBadIngredient())
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse createOrderWithoutIngredients() {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse getOrders() {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .when()
                .get(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse getOrders(String token) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .and()
                .when()
                .get(ORDERS_URI)
                .then();
    }
}
