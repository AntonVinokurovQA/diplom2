package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import json.Credentials;

import static data.TestData.BASE_URI;
import static data.TestData.LOGIN_URI;
import static io.restassured.RestAssured.given;

public class AuthClient {
    @Step
    public ValidatableResponse logIn(Credentials credentials) {
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(credentials)
                .when()
                .post(LOGIN_URI)
                .then();
    }
}
