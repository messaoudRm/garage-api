package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("La durée d'un prêt dépend du profil")
class LoanDeskTest {

    private final LoanDesk desk = new LoanDesk();

    @Test
    void un_etudiant_garde_un_ouvrage_trente_jours() {
        assertThat(desk.daysAllowed("student")).isEqualTo(30);
    }

    @Test
    void un_enseignant_le_garde_soixante_jours() {
        assertThat(desk.daysAllowed("teacher")).isEqualTo(60);
    }

    @Test
    void tout_le_monde_le_garde_quinze_jours() {
        assertThat(desk.daysAllowed("adherent")).isEqualTo(15);
    }
}
