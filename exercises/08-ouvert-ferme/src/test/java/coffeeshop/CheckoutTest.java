package coffeeshop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("La caisse applique le tarif annoncé")
class CheckoutTest {

    private final Checkout checkout = new Checkout();

    @Test
    void le_tarif_normal_ne_retire_rien() {
        assertThat(checkout.total(Money.euros(4), "normal")).isEqualTo(Money.euros(4));
    }

    @Test
    void le_happy_hour_retire_vingt_pour_cent() {
        assertThat(checkout.total(Money.euros(4), "happy-hour")).isEqualTo(new Money(320));
    }

    @Test
    void le_tarif_etudiant_retire_dix_pour_cent() {
        assertThat(checkout.total(Money.euros(4), "student")).isEqualTo(new Money(360));
    }
}
