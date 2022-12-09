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

import static org.junit.Assert.assertNotNull;

public class EditUserPassWithAuthTest {
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
    @DisplayName("Changing user data(password)")
    @Description("Successful change of user password")
    public void changePasswordUserDataWithAuthTest() {
        String message = "Process of changing user password is failed";
        User user = User.getRandomUser();
        String newPassword = RandomStringUtils.randomAlphabetic(10);
        token = userClient.create(user);
        Credentials creds = Credentials.from(user);
        userClient.logout(token);
        token = userClient.login(creds);
        userClient.changeUserPassword(token, newPassword);
        creds.setPassword(newPassword);
        userClient.logout(token);
        token = userClient.login(creds);
        assertNotNull(message, token.getAccessToken());
        assertNotNull(message, token.getRefreshToken());
    }
}
