package devis;

import garage.app.devis.TarificationRemorquage;
import garage.domain.Montant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TarificationRemorquageTest {


    @Test
    @DisplayName("30 km de remorquage coûtent 63,00 €")
    void trente_km_de_remorquage_coutent_63_euros() {


        TarificationRemorquage tarificationRemorquage = new TarificationRemorquage() ;
        Montant prixRemoquage =  tarificationRemorquage.calculer(30) ;

assertThat(prixRemoquage.getCentimes()).isEqualTo(6300) ;

    }


    @Test
    @DisplayName("20 km de remorquage coûtent 45,00 €")
    void vingt_km_de_remorquage_coutent_45_euros() {


        TarificationRemorquage tarificationRemorquage = new TarificationRemorquage() ;
        Montant prixRemoquage =  tarificationRemorquage.calculer(20) ;

        assertThat(prixRemoquage.getCentimes()).isEqualTo(4500) ;

    }


}
