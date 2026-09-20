package coffeeshop;

public class Checkout {

    /**
     * Le tarif arrive de la caisse sous forme de texte : « happy-hour »,
     * « student », ou autre chose. Cette signature ne change pas.
     */
    public Money total(Money subtotal, String pricing) {
        switch (pricing) {
            case "happy-hour":
                return subtotal.percentOff(20);
            case "student":
                return subtotal.percentOff(10);
            default:
                return subtotal;
        }
    }
}
