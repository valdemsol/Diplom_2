package order;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private List<Ingredient> ingredients;
    private String _id;
    private OrderUser orderUser;
    private String status;
    private String name;
    private int number;
    private int price;
}
