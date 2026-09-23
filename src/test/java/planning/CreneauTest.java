package garage.planning;

import garage.domain.planning.Creneau;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CreneauTest {

    @Test
    void doit_refuser_un_creneau_qui_chevauche_un_creneau_occupe() {

        Creneau occupe = new Creneau(
                LocalTime.of(9, 0),
                LocalTime.of(11, 30)
        );

        Creneau demande = new Creneau(
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        assertFalse(demande.estDisponiblePendant(occupe));
    }

    @Test
    void doit_accepter_un_creneau_qui_commence_a_la_fin_du_creneau_occupe() {

        Creneau occupe = new Creneau(
                LocalTime.of(9, 0),
                LocalTime.of(11, 30)
        );

        Creneau demande = new Creneau(
                LocalTime.of(11, 30),
                LocalTime.of(12, 0)
        );

        assertTrue(demande.estDisponiblePendant(occupe));
    }

    @Test
    void doit_refuser_un_creneau_qui_traverse_la_pause_dejeuner() {

        Creneau demande = new Creneau(
                LocalTime.of(11, 30),
                LocalTime.of(13, 0)
        );

        assertFalse(demande.estDansLesHorairesDuGarage());
    }

    @Test
    void doit_refuser_un_creneau_dont_la_fin_est_avant_le_debut() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Creneau(
                        LocalTime.of(11, 0),
                        LocalTime.of(10, 0)
                )
        );
    }

    @Test
    void doit_accepter_un_creneau_dans_les_horaires_du_matin() {

        Creneau demande = new Creneau(
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        );

        assertTrue(demande.estDansLesHorairesDuGarage());
    }

    @Test
    void doit_accepter_un_creneau_dans_les_horaires_de_l_apres_midi() {

        Creneau demande = new Creneau(
                LocalTime.of(14, 0),
                LocalTime.of(18, 0)
        );

        assertTrue(demande.estDansLesHorairesDuGarage());
    }

    @Test
    void doit_refuser_un_creneau_qui_commence_avant_l_ouverture() {

        Creneau demande = new Creneau(
                LocalTime.of(7, 0),
                LocalTime.of(9, 0)
        );

        assertFalse(demande.estDansLesHorairesDuGarage());
    }

    @Test
    void doit_refuser_un_creneau_qui_se_termine_apres_la_fermeture() {

        Creneau demande = new Creneau(
                LocalTime.of(17, 0),
                LocalTime.of(18, 30)
        );

        assertFalse(demande.estDansLesHorairesDuGarage());
    }
}