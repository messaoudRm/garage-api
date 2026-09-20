package coffeeshop;

public record OrderLine(Drink drink, int quantity) {

    public OrderLine {
        if (quantity < 1) {
            throw new IllegalArgumentException("Une ligne contient au moins une boisson");
        }
    }

    public Money subtotal() {
        return new Money(drink.price().cents() * quantity);
    }
}
