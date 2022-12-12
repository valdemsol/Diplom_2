package order;

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

import java.util.List;

import static junit.framework.TestCase.*;

public class CreateOrderTests {
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
    @DisplayName("Order creation")
    @Description("Order creation with ingredients and auth")
    public void createOrderWithAuthTest() {
        String message = "Order is created";
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(5));
        User user = User.getRandomUser();
        token = userClient.create(user);
        Order order = orderClient.createOrderWithAuthUser(token, ingredients);
        assertEquals(message, order.getIngredients().size(), ingredients.getIngredients().size());
        for (int i = 0; i < order.getIngredients().size() - 1; i++) {
            assertEquals(message, order.getIngredients().get(i).get_id(), ingredients.getIngredients().get(i));
        }
        assertNotNull(message, order.get_id());
        assertNotNull(message, order.getStatus());
        assertTrue(message, order.getNumber() > 0);
        assertTrue(message, order.getPrice() > 0);
    }

    @Test
    @DisplayName("Order creation")
    @Description("Order creation without auth and without ingredients")
    public void createOrderWithoutAuthAndWithoutIngredientsTest() {
        String message = "Order creation is failed";
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(3));
        User user = User.getRandomUser();
        token = userClient.create(user);
        Credentials creds = Credentials.from(user);
        userClient.logout(token);
        Order order = orderClient.createOrderWithoutAuthUser(ingredients);
        token = userClient.login(creds);
        assertNull(message, order.getIngredients());
        assertNull(message, order.get_id());
        assertNull(message, order.getOrderUser());
        assertNull(message, order.getStatus());
        assertNull(message, order.getName());
        assertTrue(message, order.getNumber() > 0);
        assertEquals(message, order.getPrice(), 0);
    }

    @Test
    @DisplayName("Order creation")
    @Description("Order creation with auth and without ingredients")
    public void createOrderWithAuthAndWithoutIngredientsTest() {
        String message = "Order creation failed";
        String expected_message = "Ingredient ids must be provided";
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(3));
        User user = User.getRandomUser();
        token = userClient.create(user);
        String actual_message = orderClient.createOrderWithoutIngredients(token);
        assertEquals(message, expected_message, actual_message);
    }

    @Test
    @DisplayName("Order creation")
    @Description("Order creation with auth and with incorrect hash of ingredients")
    public void createOrderWithAuthAndWithWrongHashIngredientsTest() {
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(List.of(RandomStringUtils.randomAlphabetic(10)));
        User user = User.getRandomUser();
        token = userClient.create(user);
        orderClient.createOrderWithIncorrectHashIngredients(token, ingredients);
    }
}
