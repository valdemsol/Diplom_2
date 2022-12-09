package user;

import com.google.gson.JsonObject;
import utils.RestClient;

import static constants.Constants.USER_REGISTER;

public class UserClient extends RestClient {
    private final String LOGIN_URL = "api/auth/login";
    private final String LOGOUT_URL = "api/auth/logout";
    private final String REGISTRATION_URL = "api/auth/register";
    private final String USER_URL = "api/auth/user";

    public Token login(Credentials creds) {
        var response = reqSpec
                .body(creds)
                .when()
                .post(LOGIN_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .extract();
        return new Token(response.path("accessToken"), response.path("refreshToken"));
    }

    public String loginWithBadParams(Credentials creds) {
        return reqSpec
                .body(creds)
                .when()
                .post(LOGIN_URL)
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
    }

    public void logout(Token token) {
        var json = new JsonObject();
        json.addProperty("token", token.getRefreshToken());
        reqSpec
                .body(json.toString())
                .when()
                .post(LOGOUT_URL)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public Token create(User user) {
        var response = reqSpec
                .body(user)
                .when()
                .post(USER_REGISTER)
                .then()
                .assertThat()
                .statusCode(200)
                .extract();
        return new Token(response.path("accessToken"), response.path("refreshToken"));
    }

    public void delete(Token token) {
        reqSpec
                .header("authorization", token.getAccessToken())
                .when()
                .delete(USER_URL)
                .then()
                .assertThat()
                .statusCode(202);
    }

    public String createBadUser(User user) {
        return reqSpec
                .body(user)
                .when()
                .post(REGISTRATION_URL)
                .then()
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");
    }

    public String changeUserEmail(Token token, String email) {
        var json = new JsonObject();
        json.addProperty("email", email);
        return reqSpec
                .header("authorization", token.getAccessToken())
                .body(json.toString())
                .when()
                .patch(USER_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("user.email");
    }

    public String changeUserName(Token token, String name) {
        var json = new JsonObject();
        json.addProperty("name", name);
        return reqSpec
                .header("authorization", token.getAccessToken())
                .body(json.toString())
                .when()
                .patch(USER_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("user.name");
    }

    public void changeUserPassword(Token token, String password) {
        var json = new JsonObject();
        json.addProperty("password", password);
        reqSpec
                .header("authorization", token.getAccessToken())
                .body(json.toString())
                .when()
                .patch(USER_URL)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public String changeUserRejection(String json) {
        return reqSpec
                .body(json)
                .when()
                .patch(USER_URL)
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
    }

    public String getUserEmail(Token token) {
        return reqSpec
                .header("authorization", token.getAccessToken())
                .when()
                .patch(USER_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("user.email");
    }
}
