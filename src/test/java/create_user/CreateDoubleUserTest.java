package create_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.Token;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertEquals;

public class CreateDoubleUserTest {
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
    @DisplayName("Registration of double user")
    @Description("Failed creation of double user")
    public void createDoubleUserTests() {
        String message = "Creation of double user is impossible";
        String expected = "User already exists";
        User user = User.getRandomUser();
        token = userClient.create(user);
        String actual = userClient.createBadUser(user);
        assertEquals(message, expected, actual);
    }
}
