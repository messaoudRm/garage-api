package devis;

import garage.app.devis.Forfait;
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




    @Test
    @DisplayName("US-3 · forfait plaquettes avant coûte 149 €")
    void forfait_plaquettes_coute_149_euros_quelle_que_soit_la_duree() {

        Tarification plaquettes =
                new Forfait("Vidange", Montant.euros(149));

        Montant prix25Minutes =
                plaquettes.calculer(Duration.ofMinutes(25));

        Montant prix2Heures10 =
                plaquettes.calculer(Duration.ofMinutes(130));

        assertThat(prix25Minutes.getCentimes())
                .isEqualTo(14900);

        assertThat(prix2Heures10.getCentimes())
                .isEqualTo(14900);
    }



}
