package planning;

import garage.app.planning.Baie;
import garage.domain.Atelier;
import garage.domain.Vehicule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaieTest {

    @Test
    void doit_refuser_un_vehicule_de_3200kg_sur_un_pont_de_2500kg() {

        Baie pont25 = Baie.pont(2500);

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 308",
                3200
        );

        assertFalse(pont25.peutAccueillir(vehicule));
    }

    @Test
    void doit_accepter_un_vehicule_de_3200kg_sur_un_pont_de_3500kg() {

        Baie pont35 = Baie.pont(3500);

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 308",
                3200
        );

        assertTrue(pont35.peutAccueillir(vehicule));
    }

    @Test
    void doit_accepter_un_vehicule_de_1400kg_sur_un_pont_de_2500kg() {

        Baie pont25 = Baie.pont(2500);

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 208",
                1400
        );

        assertTrue(pont25.peutAccueillir(vehicule));
    }

    @Test
    void doit_accepter_une_operation_de_mecanique_sur_un_pont() {

        Baie pont25 = Baie.pont(2500);

        assertTrue(
                pont25.estCompatibleAvec(Atelier.MECANIQUE)
        );
    }

    @Test
    void doit_accepter_une_operation_de_mecanique_dans_une_fosse() {

        Baie fosse = Baie.fosse();

        assertTrue(
                fosse.estCompatibleAvec(Atelier.MECANIQUE)
        );
    }

    @Test
    void doit_refuser_une_operation_de_carrosserie_sur_un_pont() {

        Baie pont25 = Baie.pont(2500);

        assertFalse(
                pont25.estCompatibleAvec(Atelier.CARROSSERIE)
        );
    }

    @Test
    void doit_accepter_une_operation_de_carrosserie_dans_la_cabine_de_peinture() {

        Baie cabine = Baie.cabinePeinture();

        assertTrue(
                cabine.estCompatibleAvec(Atelier.CARROSSERIE)
        );
    }
}