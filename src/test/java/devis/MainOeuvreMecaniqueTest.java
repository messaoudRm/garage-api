package devis;

import garage.app.devis.MainOeuvreMecanique;
import garage.domain.Montant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MainOeuvreMecaniqueTest {


    @Test
    @DisplayName("US-1 · 1h45 de main-d'œuvre coûte 108,50 €")
    void une_heure_quarante_cinq_coute_108_50_euros() {

        MainOeuvreMecanique mainOeuvre = new MainOeuvreMecanique();

        Montant montant = mainOeuvre.totalMainOeuvre(
                Duration.ofMinutes(105)
        );

        assertThat(montant.getCentimes()).isEqualTo(10850);


    }

    @Test
    @DisplayName("US-1 · 1h35 de main-d'œuvre coûte 108,50 € facturée comme 1h45,")
    void une_heure_trente_cinq_108_50_euros() {

        MainOeuvreMecanique mainOeuvre = new MainOeuvreMecanique() ;

 Montant montant = mainOeuvre.totalMainOeuvre(Duration.ofMinutes(95));

assertThat(montant.getCentimes()).isEqualTo(10850) ;

    }

}
