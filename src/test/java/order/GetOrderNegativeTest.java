package order;

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

public class GetOrderNegativeTest {
    private UserClient userClient;
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
    @DisplayName("Getting orders without auth")
    @Description("Getting orders for not auth user")
    public void getOrderNegativeTest() {
        String message = "Getting orders is failed";
        String expectedMessage = "You should be authorised";
        OrderClient orderClient = new OrderClient();
        OrderClient orderClientNew = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(5));
        User user = User.getRandomUser();
        token = userClient.create(user);
        Credentials creds = Credentials.from(user);
        userClient.logout(token);
        String actualMessage = orderClientNew.getOrderWithoutUserAuth();
        token = userClient.login(creds);
        assertEquals(message, actualMessage, expectedMessage);
    }
}
