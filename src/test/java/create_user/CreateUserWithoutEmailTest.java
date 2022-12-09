package create_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertEquals;

public class CreateUserWithoutEmailTest {
    private UserClient userClient;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Registration without email")
    @Description("Failed registration without email")
    public void createUserEmptyEmailTest() {
        String message = "Registration failed";
        String expected = "Email, password and name are required fields";
        User user = User.getRandomUserWithoutEmail("");
        String actual = userClient.createBadUser(user);
        assertEquals(message, expected, actual);
    }
}
