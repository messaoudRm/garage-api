package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Le panier de réservation")
class BasketDeskTest {

    private final BasketDesk desk = new BasketDesk();
    private final Basket basket = new Basket();

    @Test
    void un_ouvrage_reserve_entre_dans_le_panier() {
        desk.addToBasket(basket, new Book("Atlas"), 1);

        assertThat(basket.lines()).containsExactly(new Line(new Book("Atlas"), 1));
    }

    @Test
    void deux_ouvrages_font_deux_lignes() {
        desk.addToBasket(basket, new Book("Atlas"), 1);
        desk.addToBasket(basket, new Book("Larousse"), 2);

        assertThat(basket.lines()).hasSize(2);
    }
}
