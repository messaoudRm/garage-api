package devis;

import garage.app.devis.LignePiece;
import garage.app.devis.MainOeuvreMecanique;
import garage.domain.Montant;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DevisTest {

    @Test
    void devis_additionne_main_oeuvre_et_pieces() {
        MainOeuvreMecanique mainOeuvreMecanique = new MainOeuvreMecanique() ;
        LignePiece plaquettes = new LignePiece("plaquettes", Montant.centimes(4390),4) ;
        Montant prixMainOeuvre = mainOeuvreMecanique.totalMainOeuvre(Duration.ofMinutes(105));
        Montant pieces = plaquettes.montant();

        Devis devis = new Devis(prixMainOeuvre , pieces) ;


assertThat(devis.total.getCentimes()).isEqualTo(28410) ;
    }
}
