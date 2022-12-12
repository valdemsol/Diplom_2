package login_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.Credentials;
import user.Token;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertNotNull;

public class UserSuccessfulLoginTest {
    private static UserClient userClient;
    private Token token;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @After
    public void teardown() {
        userClient.delete(token);
    }

    @Test
    @DisplayName("User auth")
    @Description("Successful user authorization check")
    public void userLoginSuccessfulTest() {
        String message = "Auth is successful";
        User user = User.getRandomUser();
        token = userClient.create(user);
        Credentials creds = Credentials.from(user);
        userClient.logout(token);
        token = userClient.login(creds);
        assertNotNull(message, token.getAccessToken());
        assertNotNull(message, token.getRefreshToken());
    }
}
