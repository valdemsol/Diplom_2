package order;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import utils.RestClient;
import user.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static constants.Constants.INGREDIENTS;
import static constants.Constants.ORDERS;

public class OrderClient extends RestClient {
    public List<String> getListIngredients(int countIngredients) {
        Random rand = new Random();
        List<String> allIdIngredients = reqSpec
                .when()
                .get(INGREDIENTS)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("data._id");
        List<String> ingredientsForBurger = new ArrayList<>();
        for (int i = 0; i < countIngredients; i++) {
            ingredientsForBurger.add(allIdIngredients.get(rand.nextInt(allIdIngredients.size())));
        }
        return ingredientsForBurger;
    }

    @Step("Order creation with user auth")
    public Order createOrderWithAuthUser(Token token, Ingredients ingredientsForBurger) {
        var response = reqSpec
                .header("authorization", token.getAccessToken())
                .body(ingredientsForBurger)
                .when()
                .post(ORDERS)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();
        JsonObject element = new Gson().fromJson(response, JsonObject.class).getAsJsonObject("order");
        return new Gson().fromJson(element, Order.class);
    }

    @Step("Order creation without user auth")
    public Order createOrderWithoutAuthUser(Ingredients ingredientsForBurger) {
        var response = reqSpec
                .body(ingredientsForBurger)
                .when()
                .post(ORDERS)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();
        JsonObject element = new Gson().fromJson(response, JsonObject.class).getAsJsonObject("order");
        return new Gson().fromJson(element, Order.class);
    }

    @Step("Order creation without ingredients")
    public String createOrderWithoutIngredients(Token token) {
        return reqSpec
                .header("authorization", token.getAccessToken())
                .when()
                .post(ORDERS)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Order creation with incorrect ingredients hash")
    public void createOrderWithIncorrectHashIngredients(Token token, Ingredients ingredientsForBurger) {
        reqSpec
                .header("authorization", token.getAccessToken())
                .body(ingredientsForBurger)
                .when()
                .post(ORDERS)
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Step("Getting order with user auth")
    public List<Integer> getOrderWithUserAuth(Token token) {
        return reqSpec
                .header("Authorization", token.getAccessToken())
                .when()
                .get(ORDERS)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("orders.number");
    }

    @Step("Getting order without user auth")
    public String getOrderWithoutUserAuth() {
        return reqSpec
                .when()
                .get(ORDERS)
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
    }
}
