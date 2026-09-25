package devis;

import garage.domain.Montant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TarificationRemorquageTest {


    @Test
    void trente_km_de_remorquage_coutent_63_euros() {


        TarificationRemorquage tarificationRemorquage = new TarificationRemorquage() ;
        Montant prixRemoquage =  tarificationRemorquage.calculer(30) ;

assertThat(prixRemoquage.getCentimes()).isEqualTo(6300) ;

    }


}
