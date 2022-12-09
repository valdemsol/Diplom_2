package edit_data_user;

import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.Credentials;
import user.Token;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertEquals;

public class EditUserDataWithoutAuthTest {
    private UserClient userClient;
    private UserClient userClient_delete;
    private Token token;

    @Before
    public void setup() {
        userClient = new UserClient();
        userClient_delete = new UserClient();
    }

    @After
    public void teardown() {
        userClient_delete.delete(token);
    }

    @Test
    @DisplayName("Unsuccessful editing of user data")
    @Description("Unsuccessful editing user data without auth")
    public void changeUserDataWithoutAuthTest() {
        String message = "Unauthorised user cannot change user data";
        String expectedMessage = "You should be authorised";
        User user = User.getRandomUser();
        var jsonWithNewEmail = new JsonObject();
        jsonWithNewEmail.addProperty("email", RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yandex.ru");
        token = userClient.create(user);
        String expectedOldEmail = user.getEmail();
        Credentials creds = Credentials.from(user);
        userClient.logout(token);
        String actualMessage = userClient.changeUserRejection(jsonWithNewEmail.toString());
        token = userClient.login(creds);
        assertEquals(message, expectedMessage, actualMessage);
        assertEquals(message, expectedOldEmail, userClient.getUserEmail(token));
    }
}
