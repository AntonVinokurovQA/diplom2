package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import json.User;

import static data.TestData.BASE_URI;
import static data.TestData.NEW_USER_URI;
import static io.restassured.RestAssured.given;

public class UserClient {
    @Step
    public ValidatableResponse createUser(User user){
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .post(NEW_USER_URI)
                .then();
    }
}
