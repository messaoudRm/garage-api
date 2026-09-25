package devis;

import garage.app.devis.TarificationCarrosserie;
import garage.domain.Montant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TarificationCarrosserieTest {


    @Test
    @DisplayName("US-2 · 2 h de carrosserie coûtent 156,00 €")
    void deux_heures_de_carrosserie_coutent_156_euros() {

        TarificationCarrosserie tarification =
                new TarificationCarrosserie();

        Montant montant =
                tarification.calculer(Duration.ofHours(2));

        assertThat(montant.getCentimes())
                .isEqualTo(15600);
    }


    @Test
    @DisplayName("US-2 . Le quart d'heure entamé s'applique dans les trois ateliers : 31 min de carrosserie → 39,00 €")
    void trente_et_une_minutes_de_carrosserie_coutent_39_euros() {

        TarificationCarrosserie tarification = new TarificationCarrosserie();
        Montant montant = tarification.calculer(Duration.ofMinutes(31));

        assertThat(montant.getCentimes()).isEqualTo(5850);
    }

}
