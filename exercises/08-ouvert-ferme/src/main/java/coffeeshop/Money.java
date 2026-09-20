package coffeeshop;

/** Un montant, en centimes : jamais de double pour de l'argent. */
public record Money(long cents) {

    public Money {
        if (cents < 0) {
            throw new IllegalArgumentException("Un montant n'est jamais négatif");
        }
    }

    public static Money euros(long euros) {
        return new Money(euros * 100);
    }

    /** Arrondi au centime inférieur, comme la caisse. */
    public Money percentOff(int percent) {
        return new Money(cents * (100 - percent) / 100);
    }
}
