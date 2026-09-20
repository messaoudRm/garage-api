package library;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Rendre un prêt")
class ReturnDeskTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 9, 21);

    private final ReturnDesk desk = new ReturnDesk();
    private final Loan loan = new Loan();

    @Test
    void un_pret_rendu_porte_sa_date_de_retour() {
        desk.giveBack(loan, TODAY);

        assertThat(loan.isReturned()).isTrue();
        assertThat(loan.returnedOn()).isEqualTo(TODAY);
    }

    @Test
    void un_pret_ne_se_rend_pas_deux_fois() {
        desk.giveBack(loan, TODAY);

        assertThatThrownBy(() -> desk.giveBack(loan, TODAY)).isInstanceOf(AlreadyReturned.class);
    }
}
