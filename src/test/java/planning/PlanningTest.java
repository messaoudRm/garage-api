package planning;

import garage.app.planning.*;
import garage.domain.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        Baie pont25 = Baie.pont(2500);
        Baie pont35 = Baie.pont(3500);

        Planning planning = new Planning(
                pont25,
                pont35
        );

        Planning.Resultat resultat =
                planning.chercherCreneau(
                        dossier,
                        LocalTime.of(8, 0)
                );

        assertTrue(resultat.estDisponible());

        assertSame(
                pont35,
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

    @Test
    void doit_trouver_le_premier_creneau_libre_sur_une_baie_occupee() {

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 208",
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

        Baie pont25 = Baie.pont(
                2500,
                new Creneau(
                        LocalTime.of(8, 0),
                        LocalTime.of(10, 0)
                )
        );

        Planning planning = new Planning(pont25);

        Planning.Resultat resultat =
                planning.chercherCreneau(
                        dossier,
                        LocalTime.of(8, 0)
                );

        assertTrue(resultat.estDisponible());

        assertSame(
                pont25,
                resultat.baie()
        );

        assertEquals(
                new Creneau(
                        LocalTime.of(10, 0),
                        LocalTime.of(12, 0)
                ),
                resultat.creneau()
        );
    }

    @Test
    void doit_ignorer_une_baie_trop_petite_et_choisir_la_suivante() {

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

        Baie pont25 = Baie.pont(2500);
        Baie pont35 = Baie.pont(3500);

        Planning planning = new Planning(
                pont25,
                pont35
        );

        Planning.Resultat resultat =
                planning.chercherCreneau(
                        dossier,
                        LocalTime.of(8, 0)
                );

        assertTrue(resultat.estDisponible());

        assertSame(
                pont35,
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

    @Test
    void doit_refuser_si_aucune_baie_ne_permet_un_creneau_de_trois_heures() {

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 308",
                1400
        );

        Operation operation = new Operation(
                Atelier.MECANIQUE,
                Duration.ofHours(3)
        );

        Dossier dossier = new Dossier(
                new Client("Yassine"),
                vehicule,
                List.of(operation)
        );

        Baie pont25 = Baie.pont(
                2500,
                new Creneau(
                        LocalTime.of(8, 0),
                        LocalTime.of(12, 0)
                ),
                new Creneau(
                        LocalTime.of(14, 0),
                        LocalTime.of(18, 0)
                )
        );

        Planning planning = new Planning(pont25);

        Planning.Resultat resultat =
                planning.chercherCreneau(
                        dossier,
                        LocalTime.of(8, 0)
                );

        assertFalse(resultat.estDisponible());

        assertEquals(
                "Aucun créneau compatible disponible aujourd'hui",
                resultat.raison()
        );
    }

    @Test
    void doit_choisir_le_creneau_le_plus_tot_parmi_les_baies_compatibles() {

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 308",
                1400
        );

        Operation operation = new Operation(
                Atelier.MECANIQUE,
                Duration.ofHours(3)
        );

        Dossier dossier = new Dossier(
                new Client("Yassine"),
                vehicule,
                List.of(operation)
        );

        Baie pont25 = Baie.pont(
                2500,
                new Creneau(
                        LocalTime.of(8, 0),
                        LocalTime.of(14, 0)
                )
        );

        Baie pont35 = Baie.pont(3500);

        Planning planning = new Planning(
                pont25,
                pont35
        );

        Planning.Resultat resultat =
                planning.chercherCreneau(
                        dossier,
                        LocalTime.of(8, 0)
                );

        assertTrue(resultat.estDisponible());

        assertSame(
                pont35,
                resultat.baie()
        );

        assertEquals(
                new Creneau(
                        LocalTime.of(8, 0),
                        LocalTime.of(11, 0)
                ),
                resultat.creneau()
        );
    }

    @Test
    void doit_retourner_une_raison_si_aucune_baie_compatible_n_est_disponible() {

        Vehicule vehicule = new Vehicule(
                "AA-123-AA",
                "Peugeot 308",
                1400
        );

        Operation operation = new Operation(
                Atelier.MECANIQUE,
                Duration.ofHours(3)
        );

        Dossier dossier = new Dossier(
                new Client("Yassine"),
                vehicule,
                List.of(operation)
        );

        Baie pont25 = Baie.pont(
                2500,
                new Creneau(
                        LocalTime.of(8, 0),
                        LocalTime.of(12, 0)
                ),
                new Creneau(
                        LocalTime.of(14, 0),
                        LocalTime.of(18, 0)
                )
        );

        Baie pont35 = Baie.pont(
                3500,
                new Creneau(
                        LocalTime.of(8, 0),
                        LocalTime.of(12, 0)
                ),
                new Creneau(
                        LocalTime.of(14, 0),
                        LocalTime.of(18, 0)
                )
        );

        Planning planning = new Planning(
                pont25,
                pont35
        );

        Planning.Resultat resultat =
                planning.chercherCreneau(
                        dossier,
                        LocalTime.of(8, 0)
                );

        assertFalse(resultat.estDisponible());

        assertEquals(
                "Aucun créneau compatible disponible aujourd'hui",
                resultat.raison()
        );
    }
}