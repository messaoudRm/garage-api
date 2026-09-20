package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Rendre un ouvrage solde l'amende")
class ReturnDeskTest {

    private final Fines fines = new Fines();
    private final ReturnDesk desk = new ReturnDesk(fines);

    @Test
    void l_amende_est_soldee_au_retour() {
        desk.giveBack("pret-42");

        assertThat(fines.settled()).containsExactly("pret-42");
    }

    @Test
    void deux_retours_soldent_deux_amendes() {
        desk.giveBack("pret-42");
        desk.giveBack("pret-43");

        assertThat(fines.settled()).containsExactly("pret-42", "pret-43");
    }
}
