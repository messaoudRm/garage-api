package library;

/** L'écran de réservation. Cette signature ne change pas. */
public class BasketDesk {

    public void addToBasket(Basket basket, Book book, int quantity) {
        basket.lines().add(new Line(book, quantity));
    }
}
