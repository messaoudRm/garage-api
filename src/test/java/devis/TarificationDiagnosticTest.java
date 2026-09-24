package devis;

import garage.app.devis.TarificationDiagnostic;
import garage.domain.Montant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TarificationDiagnosticTest {
    @Test
    @DisplayName("US-2 · 30 min de diagnostic coûtent 47,50 €")
    void trente_minutes_de_diagnostic_coutent_47_50_euros() {

        TarificationDiagnostic tarification =
                new TarificationDiagnostic();

        Montant montant =
                tarification.calculer(Duration.ofMinutes(30));

        assertThat(montant.getCentimes())
                .isEqualTo(4750);
    }
}
