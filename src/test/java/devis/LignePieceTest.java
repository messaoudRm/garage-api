package devis;

import garage.domain.Montant;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class LignePieceTest {



    @Test
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
