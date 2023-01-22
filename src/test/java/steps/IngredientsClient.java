package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import java.util.List;

import static data.TestData.BASE_URI;
import static data.TestData.INGREDIENTS_URI;
import static io.restassured.RestAssured.given;

public class IngredientsClient {
    @Step
    public List<String> getListOfIngredientsId(){
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .when()
                .get(INGREDIENTS_URI)
                .then().extract().path("data._id");
    }
}
