package devis;

import garage.app.devis.Tarification;
import garage.domain.Montant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ForfaitTest {


    @Test
    @DisplayName("US-3 · forfait vidange coûte 89 € quelle que soit la durée")
    void forfait_vidange_coute_89_euros_quelle_que_soit_la_duree() {

        Tarification vidange =
                new Forfait("Vidange", Montant.euros(89));

        Montant prix25Minutes =
                vidange.calculer(Duration.ofMinutes(25));

        Montant prix2Heures10 =
                vidange.calculer(Duration.ofMinutes(130));

        assertThat(prix25Minutes.getCentimes())
                .isEqualTo(8900);

        assertThat(prix2Heures10.getCentimes())
                .isEqualTo(8900);
    }






}
