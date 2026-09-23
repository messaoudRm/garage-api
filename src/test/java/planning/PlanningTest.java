package garage.planning;

import garage.app.planning.Creneau;
import garage.app.planning.Planning;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}