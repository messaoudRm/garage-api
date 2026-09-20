package coffeeshop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("US-1 · Prendre une commande")
class OrderTest {

    @Test
    void a_new_order_costs_nothing() {
        Order order = Order.open();

        assertThat(order.total()).isEqualTo(new Money(0));
    }

    // À vous · critère 2 : « Un espresso coûte 2 € »

    // À vous · critère 3 : « Deux boissons différentes s'additionnent »
}
