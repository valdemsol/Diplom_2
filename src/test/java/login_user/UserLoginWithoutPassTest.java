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

import static org.junit.Assert.assertEquals;

public class UserLoginWithoutPassTest {
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
    @DisplayName("Auth with invalid pass")
    @Description("Auth chech with invalid pass")
    public void loginUserWithoutPasswordTest() {
        String message = "Auth is failed";
        String expected = "email or password are incorrect";
        User user = User.getRandomUser();
        token = userClient.create(user);
        userClient.logout(token);
        Credentials creds = Credentials.from(user);
        creds.UserCredentialsBadPassword(creds);
        String actual = userClient.loginWithBadParams(creds);
        assertEquals(message, expected, actual);
    }
}
