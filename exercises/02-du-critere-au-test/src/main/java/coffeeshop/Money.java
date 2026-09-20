package coffeeshop;

public record Money(long cents) {

    public Money {
        if (cents < 0) {
            throw new IllegalArgumentException("Un montant n'est jamais négatif");
        }
    }

    public static Money euros(long euros) {
        return new Money(euros * 100);
    }
}
