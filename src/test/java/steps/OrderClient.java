package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import json.OrderGenerator;

import static data.TestData.*;
import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step
    public ValidatableResponse createOrderWithIngredients(){
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(OrderGenerator.getRandomOrder())
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse createOrderWithIngredients(String token){
        return given().log().all()
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
    public ValidatableResponse createOrderWithBadIngredient(){
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(OrderGenerator.getOrderWithBadIngredient())
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse createOrderWithoutIngredients(){
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse getOrders(){
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .when()
                .get(ORDERS_URI)
                .then();
    }

    @Step
    public ValidatableResponse getOrders(String token){
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .and()
                .when()
                .get(ORDERS_URI)
                .then();
    }
}
