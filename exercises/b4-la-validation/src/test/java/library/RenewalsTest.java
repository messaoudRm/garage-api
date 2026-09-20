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
        assertThat(renewals.renew(new Loan("Dune", false))).isEqualTo("Dune prolongé");
    }

    @Test
    void un_ouvrage_de_reference_ne_se_prolonge_pas() {
        assertThatThrownBy(() -> renewals.renew(new Loan("Atlas", true)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
