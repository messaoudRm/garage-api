package garage.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MontantTest {

    @Test
    void doit_creer_un_montant_en_euros() {
        Montant montant = Montant.euros(89);

        assertEquals(8900, montant.getCentimes());
    }

    @Test
    void doit_creer_un_montant_en_centimes() {
        Montant montant = Montant.centimes(8523);

        assertEquals(8523, montant.getCentimes());
    }

    @Test
    void doit_refuser_un_montant_negatif_en_centimes() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Montant.centimes(-1)
        );
    }

    @Test
    void doit_additionner_deux_montants() {
        Montant premier = Montant.euros(100);
        Montant second = Montant.euros(50);

        Montant resultat = Montant.ajouter(
                premier,
                second
        );

        assertEquals(15000, resultat.getCentimes());
    }

    @Test
    void doit_multiplier_un_montant_par_une_quantite() {
        Montant prix = Montant.centimes(4390);

        Montant resultat = Montant.multiplierPar(
                prix,
                4
        );

        assertEquals(17560, resultat.getCentimes());
    }

    @Test
    void doit_refuser_une_quantite_negative() {
        Montant prix = Montant.euros(43);

        assertThrows(
                IllegalArgumentException.class,
                () -> Montant.multiplierPar(prix, -1)
        );
    }

    @Test
    void doit_etre_plus_petit_qu_un_autre_montant() {
        Montant premier = Montant.euros(50);
        Montant second = Montant.euros(100);

        assertEquals(-1, premier.compareTo(second));
    }

    @Test
    void doit_etre_egal_a_un_autre_montant() {
        Montant premier = Montant.euros(100);
        Montant second = Montant.centimes(10000);

        assertEquals(0, premier.compareTo(second));
    }

    @Test
    void doit_etre_plus_grand_qu_un_autre_montant() {
        Montant premier = Montant.euros(150);
        Montant second = Montant.euros(100);

        assertEquals(1, premier.compareTo(second));
    }
}