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

public class EditUserLoginWithAuthTest {
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
    @DisplayName("Changing user data(login)")
    @Description("Successful change of user login")
    public void changeLoginUserDataWithAuthTest() {
        String message = "Process of changing user login is failed";
        User user = User.getRandomUser();
        String expectedNewName = RandomStringUtils.randomAlphabetic(10);
        token = userClient.create(user);
        Credentials creds = Credentials.from(user);
        userClient.logout(token);
        token = userClient.login(creds);
        String actualNewName = userClient.changeUserName(token, expectedNewName);
        assertEquals(message, expectedNewName, actualNewName);
    }
}
