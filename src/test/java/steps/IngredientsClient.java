package steps;

import io.restassured.http.ContentType;

import java.util.List;

import static data.TestData.BASE_URI;
import static data.TestData.INGREDIENTS_URI;
import static io.restassured.RestAssured.given;

public class IngredientsClient {
    public List<String> getListOfIngredients(){
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .and()
                .when()
                .get(INGREDIENTS_URI)
                .then().log().all().extract().path("data._id");
    }
}
