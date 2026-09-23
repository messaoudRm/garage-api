package devis;

import garage.app.devis.LignePiece;
import garage.domain.Montant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class LignePieceTest {



    @Test
    @DisplayName("Quatre plaquettes à 43,90 € coûtent 175,60 €")
    void quatre_plaquettes_a_43_90_coutent_175_60_euros() {
        LignePiece ligne = new LignePiece(
                "Plaquette",
                Montant.centimes(4390),
                4
        );

        assertThat(ligne.montant().getCentimes())
                .isEqualTo(17560);
    }

}
