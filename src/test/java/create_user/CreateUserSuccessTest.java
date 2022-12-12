package create_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.Token;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertNotNull;

public class CreateUserSuccessTest {
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
    @DisplayName("User creation")
    @Description("Successful user registration test")
    public void createUserPositiveTest() {
        String message = "Регистрация пользователя";
        User user = User.getRandomUser();
        token = userClient.create(user);
        assertNotNull(message, token.getAccessToken());
        assertNotNull(message, token.getRefreshToken());
    }
}
