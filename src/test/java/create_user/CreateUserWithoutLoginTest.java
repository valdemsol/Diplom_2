package create_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertEquals;

public class CreateUserWithoutLoginTest {
    private UserClient userClient;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Registration without login")
    @Description("Failed registration without login")
    public void createUserEmptyLoginTest() {
        String message = "Failed registration of user";
        String expected = "Email, password and name are required fields";
        User user = User.getRandomUserWithoutName("");
        String actual = userClient.createBadUser(user);
        assertEquals(message, expected, actual);
    }
}
