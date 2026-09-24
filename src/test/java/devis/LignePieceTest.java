package devis;

import garage.app.devis.LignePiece;
import garage.domain.Montant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LignePieceTest {


    @Test
    @DisplayName("US-1 Quatre plaquettes à 43,90 € coûtent 175,60 €")
    void quatre_plaquettes_a_43_90_coutent_175_60_euros() {
        LignePiece ligne = new LignePiece(
                "Plaquette",
                Montant.centimes(4390),
                4
        );

        assertThat(ligne.montant().getCentimes())
                .isEqualTo(17560);
    }


    @Test
    @DisplayName("US-1 Une ligne de pièce avec une quantité zéro est refusée")
    void une_ligne_piece_avec_zero_exemplaire_est_refusee() {

        assertThatThrownBy(() -> new LignePiece(
                "plaquette",
                Montant.centimes(2345),
                0)
        ).isInstanceOf(IllegalArgumentException.class);

    }


}
