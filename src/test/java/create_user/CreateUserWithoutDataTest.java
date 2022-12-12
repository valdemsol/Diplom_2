package create_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertEquals;

public class CreateUserWithoutDataTest {
    private UserClient userClient;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Registration without required fields")
    @Description("Registration without required fields failed")
    public void createUserEmptyDataTest() {
        String message = "Registration failed";
        String expected = "Email, password and name are required fields";
        User user = User.getEmptyUser();
        String actual = userClient.createBadUser(user);
        assertEquals(message, expected, actual);
    }
}
