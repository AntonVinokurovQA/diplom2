package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import json.User;

import static data.TestData.*;
import static io.restassured.RestAssured.given;

public class UserClient {
    @Step
    public ValidatableResponse createUser(User user) {
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .post(NEW_USER_URI)
                .then();
    }

    @Step
    public ValidatableResponse getUser(String token) {
        return given().log().all()
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
        return given().log().all()
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
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .patch(USER_URI)
                .then();
    }
}
