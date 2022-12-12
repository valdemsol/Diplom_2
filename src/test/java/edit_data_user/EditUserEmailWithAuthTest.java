package edit_data_user;

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

public class EditUserEmailWithAuthTest {
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
    @DisplayName("Changing user data(email)")
    @Description("Successful change of user email")
    public void changeEmailUserDataWithAuthTest() {
        String message = "Process of changing user email is failed";
        User user = User.getRandomUser();
        String expectedNewEmail = (RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yandex.ru");
        token = userClient.create(user);
        Credentials creds = Credentials.from(user);
        userClient.logout(token);
        token = userClient.login(creds);
        String actualNewEmail = userClient.changeUserEmail(token, expectedNewEmail);
        assertEquals(message, expectedNewEmail, actualNewEmail);
    }
}
