package restarunt;

import java.util.List;

public class OrderItem {
    private String foodItem;
    private int quantity;

    public OrderItem(String foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }
}

