package devis;

import garage.domain.Montant;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MainOeuvreMecaniqueTest {


    @Test
    void une_heure_quarante_cinq_coute_108_50_euros() {

        MainOeuvreMecanique mainOeuvre = new MainOeuvreMecanique();

        Montant montant = mainOeuvre.calculer(
                Duration.ofMinutes(105)
        );

        assertThat(montant.getCentimes()).isEqualTo(10850);


    }

}
