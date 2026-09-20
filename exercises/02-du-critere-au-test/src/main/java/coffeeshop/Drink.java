package coffeeshop;

public enum Drink {

    ESPRESSO(200),
    LATTE(350);

    private final long cents;

    Drink(long cents) {
        this.cents = cents;
    }

    public Money price() {
        return new Money(cents);
    }
}
