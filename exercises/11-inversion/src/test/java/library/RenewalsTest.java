package library;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Prolonger un prêt")
class RenewalsTest {

    private final Renewals renewals = new Renewals();

    @Test
    void un_ouvrage_ordinaire_se_prolonge() {
        assertThat(renewals.renew("L-1")).isEqualTo("L-1 prolongé");
    }

    @Test
    void un_ouvrage_de_reference_ne_se_prolonge_pas() {
        assertThatThrownBy(() -> renewals.renew("R-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
