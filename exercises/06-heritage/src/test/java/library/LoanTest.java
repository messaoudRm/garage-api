package library;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Un prêt connaît sa date d'échéance")
class LoanTest {

    @Test
    void quinze_jours_apres_l_emprunt() {
        Loan loan = new Loan(LocalDate.of(2026, 9, 21), 15);

        assertThat(loan.dueDate()).isEqualTo(LocalDate.of(2026, 10, 6));
    }
}
