package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Le kiosque de prêt")
class BorrowKioskTest {

    private final BorrowKiosk kiosk = new BorrowKiosk();

    @Test
    void il_prete_un_ouvrage() {
        assertThat(kiosk.borrow("L-1")).isEqualTo("L-1 emprunté");
    }

    @Test
    void il_prete_n_importe_quelle_reference() {
        assertThat(kiosk.borrow("D-9")).isEqualTo("D-9 emprunté");
    }
}
