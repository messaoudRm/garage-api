package coffeeshop;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<OrderLine> lines = new ArrayList<>();

    public static Order open() {
        return new Order();
    }

    public void add(Drink drink, int quantity) {
        lines.add(new OrderLine(drink, quantity));
    }

    public Money total() {
        long cents = 0;
        for (OrderLine line : lines) {
            cents += line.subtotal().cents();
        }
        return new Money(cents);
    }
}
