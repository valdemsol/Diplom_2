package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.Token;
import user.User;
import user.UserClient;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetOrderPositiveTest {
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
    @DisplayName("Getting orders with auth")
    @Description("Getting orders with authorisation")
    public void getOrderPositiveTest() {
        String message = "Getting orders is successful";
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(5));
        User user = User.getRandomUser();
        token = userClient.create(user);
        Order order0 = orderClient.createOrderWithAuthUser(token, ingredients);
        List<Integer> actualNumberOrders = orderClient.getOrderWithUserAuth(token);
        assertEquals(message, actualNumberOrders.size(), 1);
        assertEquals(message, order0.getNumber(), actualNumberOrders.get(0).intValue());
    }
}
