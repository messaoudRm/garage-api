package planning;

import garage.app.planning.*;
import garage.domain.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlanningTest {

    @Test
    void doit_accepter_un_creneau_libre_apres_un_creneau_occupe() {

        Planning planning = new Planning(
                new Creneau(
                        LocalTime.of(9, 0),
                        LocalTime.of(11, 30)
                )
        );

        Creneau demande = new Creneau(
                LocalTime.of(11, 30),
                LocalTime.of(12, 0)
        );

        assertTrue(planning.estDisponible(demande));
    }

    @Test
    void doit_refuser_un_creneau_qui_traverse_la_pause_dejeuner() {

        Planning planning = new Planning(
                new Creneau(
                        LocalTime.of(9, 0),
                        LocalTime.of(11, 30)
                )
        );

        Creneau demande = new Creneau(
                LocalTime.of(11, 30),
                LocalTime.of(13, 0)
        );

        assertFalse(planning.estDisponible(demande));
    }

    @Test
    void doit_refuser_un_creneau_qui_chevauche_un_creneau_occupe() {

        Planning planning = new Planning(
                new Creneau(
                        LocalTime.of(9, 0),
                        LocalTime.of(11, 30)
                )
        );

        Creneau demande = new Creneau(
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        assertFalse(planning.estDisponible(demande));
    }

    @Test
    void doit_refuser_un_creneau_qui_chevauche_un_des_creneaux_occupes() {

        Planning planning = new Planning(
                new Creneau(
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0)
                ),
                new Creneau(
                        LocalTime.of(14, 0),
                        LocalTime.of(15, 0)
                )
        );

        Creneau demande = new Creneau(
                LocalTime.of(14, 30),
                LocalTime.of(15, 30)
        );

        assertFalse(planning.estDisponible(demande));
    }

    @Test
    void doit_trouver_le_premier_creneau_disponible() {

        Planning planning = new Planning(
                new Creneau(
                        LocalTime.of(9, 0),
                        LocalTime.of(11, 30)
                )
        );

        Creneau resultat = planning.trouverPremierCreneauDisponible(
                LocalTime.of(9, 0),
                30
        );

        assertEquals(
                new Creneau(
                        LocalTime.of(11, 30),
                        LocalTime.of(12, 0)
                ),
                resultat
        );
    }

    @Test
    void doit_rechercher_apres_la_pause_dejeuner() {

        Planning planning = new Planning(
                new Creneau(
                        LocalTime.of(9, 0),
                        LocalTime.of(11, 30)
                )
        );

        Creneau resultat = planning.trouverPremierCreneauDisponible(
                LocalTime.of(11, 30),
                90
        );

        assertEquals(
                new Creneau(
                        LocalTime.of(14, 0),
                        LocalTime.of(15, 30)
                ),
                resultat
        );
    }

    @Test
    void doit_trouver_le_premier_creneau_pour_une_operation_du_dossier() {

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 308",
                1400
        );

        Operation operation = new Operation(
                Atelier.MECANIQUE,
                Duration.ofHours(2)
        );

        Dossier dossier = new Dossier(
                new Client("Yassine"),
                vehicule,
                List.of(operation)
        );

        Planning planning = new Planning();

        Creneau resultat = planning.trouverPremierCreneauPour(
                dossier,
                LocalTime.of(8, 0)
        );

        assertEquals(
                new Creneau(
                        LocalTime.of(8, 0),
                        LocalTime.of(10, 0)
                ),
                resultat
        );
    }

    @Test
    void doit_trouver_une_baie_compatible_avec_le_vehicule_et_l_operation() {

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 308",
                3200
        );

        Operation operation = new Operation(
                Atelier.MECANIQUE,
                Duration.ofHours(2)
        );

        Dossier dossier = new Dossier(
                new Client("Yassine"),
                vehicule,
                List.of(operation)
        );

        Planning planning = new Planning(
                Baie.pont(2500),
                Baie.pont(3500)
        );

        Planning.Resultat resultat =
                planning.chercherCreneau(dossier, LocalTime.of(8, 0));

        assertTrue(resultat.estDisponible());
        assertEquals(
                Baie.pont(3500),
                resultat.baie()
        );
        assertEquals(
                new Creneau(
                        LocalTime.of(8, 0),
                        LocalTime.of(10, 0)
                ),
                resultat.creneau()
        );
    }
}