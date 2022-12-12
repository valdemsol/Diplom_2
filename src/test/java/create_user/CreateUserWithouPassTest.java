package create_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertEquals;

public class CreateUserWithouPassTest {
    private UserClient userClient;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Registration without pass")
    @Description("Failed registration without pass")
    public void createUserEmptyPasswordTest() {
        String message = "Failed registration";
        String expected = "Email, password and name are required fields";
        User user = User.getRandomUserWithoutPassword("");
        String actual = userClient.createBadUser(user);
        assertEquals(message, expected, actual);
    }
}
