package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Le guichet des amendes")
class FineDeskTest {

    private final FineDesk desk = new FineDesk();

    @Test
    void une_amende_s_affiche_en_euros() {
        assertThat(desk.label(new Fine(430))).isEqualTo("4,30 €");
    }

    @Test
    void le_ticket_reprend_le_montant() {
        assertThat(desk.receipt(new Fine(430))).isEqualTo("À payer : 4,30 €");
    }

    @Test
    void une_amende_nulle_est_gratuite() {
        assertThat(desk.isFree(new Fine(0))).isTrue();
    }
}
