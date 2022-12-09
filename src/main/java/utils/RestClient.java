package utils;

import io.restassured.specification.RequestSpecification;
import static constants.Constants.BASE_URL;
import static io.restassured.RestAssured.given;

public class RestClient {
    public final RequestSpecification reqSpec = given()
            .header("Content-type", "application/json")
            .baseUri(BASE_URL);
}
